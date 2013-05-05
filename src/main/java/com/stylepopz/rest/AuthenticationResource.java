
package com.stylepopz.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.singly.client.SinglyAccountStorage;
import com.singly.client.SinglyApiException;
import com.singly.client.SinglyService;
import com.singly.util.JSON;
import com.stylepopz.common.exception.ApplicationException;
import com.stylepopz.model.entity.User;
import com.stylepopz.service.SpopzService;

@Path("/auth")
@Component
public class AuthenticationResource {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationResource.class); 

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@Autowired
	private SinglyService singlyService;
	
	@Autowired
	private SpopzService spopzService;
	
	@Autowired
	private SinglyAccountStorage accountStorage;

	@GET 
	@Produces("text/plain")
	@Path("{service}")
	public Response processService(@PathParam("service") String service, 
			@QueryParam("code") String authCode,
			@QueryParam("profile") String profile,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		if(service.equalsIgnoreCase("noservice"))
			service = null;

		// store the account in the session
		HttpSession session = request.getSession();
		String account = (String)session.getAttribute("account");

		// check if we are authenticating against a service, or completing an
		// authentication by handling the redirectURL
		if (StringUtils.isNotBlank(service)) {

			if (StringUtils.isNotBlank(profile)) {

				// delete the profile
				Map<String, String> postParams = new HashMap<String, String>();
				postParams.put("delete", profile + "@" + service);
				postParams.put("access_token", accountStorage.getAccessToken(account));

				// delete the profile service
				singlyService.doPostApiRequest("/profiles", null, postParams);

				// then redirect to authentication URL
				// TODO:
				//return "redirect:/authentication.html";
			}
			else {
				try {
					return Response.seeOther(new URI(singlyService.getAuthenticationUrl(account, service, 
							"http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/api/auth/noservice", null))).build();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else if (StringUtils.isNotBlank(authCode)) {
			// parse the authentication code and pass to complete the authentication
			account = singlyService.completeAuthentication(authCode);
			session.setAttribute("account", account);
		}

		// get if the user is previously authenticated
		boolean authenticated = singlyService.isAuthenticated(account);

		// if the user is authenticated get their authenticated profiles
		if (authenticated) {
			Map<String, String> profiles = getProfiles(account);
			//logger.debug("Profiles = {}", profiles);
			
			// persist into storage
			User user = new User();
			user.setId(account);
			user.setAccess_token(accountStorage.getAccessToken(account));
			user.setCode(authCode);
			
			Set<Map.Entry<String, String>> entrySet = profiles.entrySet();
		    Iterator<Map.Entry<String, String>> i = entrySet.iterator();
		    String profileId = "";
		    while(i.hasNext()){
		        Map.Entry<String, String> element = i.next();
		        //logger.debug("Key: "+element.getKey()+" ,value: "+element.getValue());
		        user.setSocialProfile(element.getKey());
		        user.setSocialProfileId(element.getValue());
		        profileId = element.getValue();
		    }

		    
			spopzService.addUser(user);

			// show preferences page
			return Response.ok("{'profileId':"+profileId+"}", MediaType.APPLICATION_JSON).build();

		}

		if(StringUtils.isNotBlank(account)){
			// user already present
			// check to see if he has set the preferences
			if(spopzService.isPrefSet(account)){
				try {
					// show Blogger page
					return Response.status(Status.CREATED).build();
				} catch (Exception e) {
					e.printStackTrace();
					return Response.status(Status.INTERNAL_SERVER_ERROR).build();
				}
			}else{
				// let him set his preferences
				try {
					//response.sendRedirect("http://localhost:8080/stylepopz-rs/preferences.html");
					return Response.status(Status.ACCEPTED).build();
				} catch (Exception e) {
					e.printStackTrace();
					return Response.status(Status.INTERNAL_SERVER_ERROR).build();
				}
			}
		}
		
		return Response.status(Status.CONFLICT).build();

	}

	public class AuthService {

		public String id;
		public String userIdentity;
		public String name;
		public Map<String, String> icons;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUserIdentity() {
			return userIdentity;
		}

		public void setUserIdentity(String userIdentity) {
			this.userIdentity = userIdentity;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Map<String, String> getIcons() {
			return icons;
		}

		public void setIcons(Map<String, String> icons) {
			this.icons = icons;
		}

	}

	public List<AuthService> getAuthServices(SinglyService singlyService, String account) {

		// new list of services
		List<AuthService> curServices = new ArrayList<AuthService>();

		// make an API call to get profiles data and add the JSON to the model
		String servicesJson = singlyService.doGetApiRequest("/services", null);

		JsonNode rootNode = JSON.parse(servicesJson);
		Map<String, JsonNode> serviceNodes = JSON.getFields(rootNode);

		// loop through the service name to objects
		for (Map.Entry<String, JsonNode> entry : serviceNodes.entrySet()) {

			// parse and add the service to the services list
			JsonNode serviceNode = entry.getValue();
			AuthService authService = new AuthService();
			authService.id = entry.getKey();
			authService.name = StringUtils.capitalize(JSON.getString(serviceNode, "name"));

			// create a map of the icons and their sizes
			Map<String, String> icons = new HashMap<String, String>();
			List<JsonNode> iconNodes = JSON.getJsonNodes(serviceNode, "icons");
			for (JsonNode iconNode : iconNodes) {
				int height = JSON.getInt(iconNode, "height");
				int width = JSON.getInt(iconNode, "width");
				String source = JSON.getString(iconNode, "source");
				String key = height + "x" + width;
				icons.put(key, source);
			}
			authService.icons = icons;
			curServices.add(authService);
		}

		// sort the services by name
		Collections.sort(curServices, new Comparator<AuthService>() {

			@Override
			public int compare(AuthService lhs, AuthService rhs) {
				return lhs.name.compareTo(rhs.name);
			}
		});

		return curServices;
	}

	public Map<String, String> getProfiles(String account) {

		Map<String, String> profiles = new HashMap<String, String>();

		// query parameters for the api call, add in access token
		Map<String, String> qparams = new LinkedHashMap<String, String>();
		qparams.put("access_token", accountStorage.getAccessToken(account));

		// make an API call to get profiles data and add the JSON to the model
		String profilesJson = "";
		try{
			profilesJson = singlyService.doGetApiRequest("/profiles", qparams);
		}catch(SinglyApiException ex){
			throw new ApplicationException("Exception Occured!! Message="+ex.getLocalizedMessage());
		}

		// parse the response, extract authenticated profiles
		JsonNode root = JSON.parse(profilesJson);
		List<String> profileNames = JSON.getFieldnames(root);
		for (String profileName : profileNames) {
			if (!profileName.equals("id")) {
				List<String> profileIds = JSON.getStrings(root, profileName);
				profiles.put(profileName, profileIds.get(0));
				//profiles.put(profileIds.get(0), profileName);
			}
		}
		return profiles;
	}

}
