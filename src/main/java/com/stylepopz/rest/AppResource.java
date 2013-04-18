
package com.stylepopz.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.singly.client.SinglyAccountStorage;
import com.singly.client.SinglyService;
import com.stylepopz.dao.SPopzDAO;
import com.stylepopz.model.Preferences;

@Path("/user")
@Component
public class AppResource {

	private static final Logger logger = LoggerFactory.getLogger(AppResource.class);

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@Autowired
	private SinglyService singlyService;

	@Autowired
	private SPopzDAO dao;

	@Autowired
	private SinglyAccountStorage accountStorage;

	/** 
	 * returns a Json Profile
	 * Generic for any social login
	 * 
	 * @param profileId
	 * @return
	 */
	@GET
	@Path("/getProfile/{profileId}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public String getProfile(@PathParam("profileId") String profileId) {

		logger.info("fetching profile info="+profileId);

		// get the profile and retrieve the access_token
		String access_token = dao.getAccessToken(profileId);
		Map<String, String> postParams = new HashMap<String, String>();
		/*postParams.put("client_id", "");
		postParams.put("client_secret", "");
		postParams.put("code", "");
		String access_token = singlyService.doGetApiRequest("/oauth/access_token", postParams);*/

		// https://api.singly.com/services/twitter/tweets?access_token=my_token
		postParams = new HashMap<String, String>();
		postParams.put("access_token", access_token);

		// invoke the profile service
		//String output = singlyService.doGetApiRequest("/services/facebook/self", postParams);
		String output = singlyService.doGetApiRequest("/profile", postParams);
		logger.info("result="+output);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode profile = null;
		//Profile profile = null;
		try {
			//Map<String, Object> userInMap = mapper.readValue(output, new TypeReference<Map<String, Object>>() {});
			profile = mapper.readValue(output, JsonNode.class);
			dao.insertProfile(profile, "profile");
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}

	@DELETE
	@Path("/del/{service}/{profileId}/{account}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delProfile(@PathParam("service") String service, 
			@PathParam("profileId") String profileId,
			@PathParam("acccount") String account) {

		logger.info("deleting profileid="+profileId);

		// delete the profile
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put("delete", profileId + "@" + service);
		postParams.put("access_token", accountStorage.getAccessToken(account));

		// delete the profile service
		singlyService.doPostApiRequest("/profiles", null, postParams);

		logger.info("deleted profile id="+profileId);

		return Response.ok().build();
	}

	/*@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putPref(JAXBElement<Preference> pref) {
		logger.info("inside prefs page");
		return Response.ok().build();
		//Preference c = pref.getValue();
		//return putAndGetResponse(c);
	}*/


	/*@POST
	@Consumes("application/x-www-form-urlencoded")
	public void setPreferences(Form formData) {

		logger.info("am in here");
		String id = (String) request.getSession().getAttribute("account");
		logger.info("account id is "+id);


		Preferences pref = new Preferences();
		Map<String, String> size = new HashMap<String, String>();
		size.put("built", "M");
		size.put("shoe", "7");
		pref.setId(id);
		pref.setSize(size);

		AppSingleton.INSTANCE.getDao().insertPreferences(pref);
	}*/

	/*@POST
	//@Consumes(MediaType.APPLICATION_XML)
	public void setPreferences(@Context HttpServletRequest request) {

		logger.info("am in here");
		String id = (String) request.getSession().getAttribute("account");
		logger.info("account id is "+id);


		Preferences pref = new Preferences();
		Map<String, String> size = new HashMap<String, String>();
		size.put("built", "M");
		size.put("shoe", "7");
		pref.setId(id);
		pref.setSize(size);

		AppSingleton.INSTANCE.getDao().insertPreferences(pref);
	}*/

	@GET
	@Path("/getPrefs")
	@Produces({MediaType.APPLICATION_XML})
	public Preferences getPrefs() {
		Preferences pref = new Preferences();
		Map<String, String> size = new HashMap<String, String>();
		size.put("shirt", "s");
		size.put("pant", "m");
		size.put("shoes", "36");
		pref.setSize(size);
		
		Map<String, String> colors = new HashMap<String, String>();
		colors.put("color", "skyblue");
		pref.setSize(colors);
		
		Map<String, String> luxurybrands = new HashMap<String, String>();
		luxurybrands.put("luxbrand", "Armani");
		pref.setSize(luxurybrands);
		
		Map<String, String> blogger_pref = new HashMap<String, String>();
		luxurybrands.put("url", "www.calvintage.com");
		pref.setSize(luxurybrands);
		
		return pref;
	}


	/*private Response putAndGetResponse() {
		Response res;

		if(true) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		return res;
	}*/

} 