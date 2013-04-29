
package com.stylepopz.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.singly.client.SinglyAccountStorage;
import com.singly.client.SinglyService;
import com.stylepopz.model.KeyValuePair;
import com.stylepopz.model.Preferences;
import com.stylepopz.service.SpopzService;
import com.sun.jersey.api.NotFoundException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;

import flexjson.JSONSerializer;

@Path("/user")
@Api(value = "/user", description = "Operations about Users information")
@Produces({MediaType.APPLICATION_JSON})
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
	private SpopzService spopzService;

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
	@ApiOperation(value = "Find Profile by ID", notes = "Profile id must be the ID of the social login", responseClass = "java.lang.String")
    @ApiErrors(value = { @ApiError(code = 400, reason = "Invalid ID supplied"),
    @ApiError(code = 404, reason = "Profile not found") })
	public String getProfile(
			@PathParam("profileId") String profileId) throws NotFoundException{

		logger.debug("fetching profile info="+profileId);
		
		// get the profile and retrieve the access_token
		String access_token = spopzService.getAccessToken(profileId, "facebook");
		if(StringUtils.isEmpty(access_token)){
			throw new NotFoundException("profile Not found");
		}
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
		logger.debug("result="+output);
		
		JsonObject o = (JsonObject)new JsonParser().parse(output);
		System.out.println("jsonobject = "+o);
		
		ObjectMapper mapper = new ObjectMapper();
		//JsonNode profile = null;
		//Profile profile = null;
		//try {
			//Map<String, Object> userInMap = mapper.readValue(output, new TypeReference<Map<String, Object>>() {});
			//profile = mapper.readValue(output, JsonNode.class);
			//dao.insertProfile(profile, "profile");
			spopzService.insertProfile(o, "profile");
		/*} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		return output;
	}
	
	@GET
	@Path("/getProfile")
	public Response showMessageForEmptyProfile(){
		return Response.serverError().entity("profileId cannot be blank").build();
	}

	@DELETE
	@Path("/del/{service}/{profileId}/{account}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delProfile(@PathParam("service") String service, 
			@PathParam("profileId") String profileId,
			@PathParam("acccount") String account) {

		logger.debug("deleting profileid="+profileId);

		// delete the profile
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put("delete", profileId + "@" + service);
		postParams.put("access_token", accountStorage.getAccessToken(account));

		// delete the profile service
		singlyService.doPostApiRequest("/profiles", null, postParams);

		logger.debug("deleted profile id="+profileId);

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
	@Path("/getPreference/{profileId}")
	public Response getPreference(@PathParam("profileId") String profileId) {
		
		if(profileId == null || profileId.trim().length() == 0) {
	        return Response.serverError().entity("profileId cannot be blank").build();
	    }

		Preferences pref = new Preferences();
		List<KeyValuePair> size = new ArrayList<KeyValuePair>();
		size.add(new KeyValuePair("shirt", "s"));
		size.add(new KeyValuePair("pant", "m"));
		size.add(new KeyValuePair("shoes", "36"));
		pref.setSize(size);
		
		List<KeyValuePair> colors = new ArrayList<KeyValuePair>();
		colors.add(new KeyValuePair("color", "skyblue"));
		colors.add(new KeyValuePair("color", "purple"));
		pref.setColors(colors);
		
		List<KeyValuePair> prints = new ArrayList<KeyValuePair>();
		prints.add(new KeyValuePair("print", "zebra"));
		prints.add(new KeyValuePair("print", "flowery"));
		pref.setPrint(colors);
		
		List<KeyValuePair> luxurybrands = new ArrayList<KeyValuePair>();
		luxurybrands.add(new KeyValuePair("luxbrand", "armani"));
		luxurybrands.add(new KeyValuePair("luxbrand", "Gucci"));
		pref.setLuxury_brands(luxurybrands);
		
		List<KeyValuePair> hi_street_brands = new ArrayList<KeyValuePair>();
		hi_street_brands.add(new KeyValuePair("hibrand", "brand 1"));
		hi_street_brands.add(new KeyValuePair("hibrand", "brand 2"));
		pref.setHi_street_brands(hi_street_brands);
		
		List<KeyValuePair> fast_fashion_brands = new ArrayList<KeyValuePair>();
		fast_fashion_brands.add(new KeyValuePair("fastfash", "express"));
		fast_fashion_brands.add(new KeyValuePair("fastfash", "gap"));
		pref.setFast_fashion_brands(fast_fashion_brands);
		
		List<KeyValuePair> indie_designers = new ArrayList<KeyValuePair>();
		indie_designers.add(new KeyValuePair("indie", "Sonas Jeans"));
		indie_designers.add(new KeyValuePair("indie", "Rachel Zoe"));
		pref.setIndie_designers(indie_designers);
		
		List<KeyValuePair> blogger_pref = new ArrayList<KeyValuePair>();
		blogger_pref.add(new KeyValuePair("url", "www.calvintage.com"));
		blogger_pref.add(new KeyValuePair("url", "www.sfstyle.com"));
		blogger_pref.add(new KeyValuePair("url", "www.karinala.com"));
		pref.setBlogger_preferences(blogger_pref);
		
		pref.setId(profileId);
		
		JSONSerializer ser = new JSONSerializer();
		ser.prettyPrint(true);
		ser.exclude("*.class");
		String str = ser.deepSerialize(pref);
		
		
		return Response.ok(str, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/getPreference")
	public Response showMessageForEmptyPreference(){
		return Response.serverError().entity("profileId cannot be blank").build();
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