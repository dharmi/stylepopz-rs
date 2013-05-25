
package com.stylepopz.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.singly.client.SinglyAccountStorage;
import com.singly.client.SinglyService;
import com.stylepopz.model.IntermediatePreference;
import com.stylepopz.model.Preference;
import com.stylepopz.model.entity.PreferenceAsJson;
import com.stylepopz.model.entity.Profile;
import com.stylepopz.model.entity.User;
import com.stylepopz.service.SpopzService;
import com.sun.jersey.api.NotFoundException;

import flexjson.JSONSerializer;

@Path("/user")
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
	public String getProfile(@PathParam("profileId") String profileId) throws NotFoundException{

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

	/*@POST
	@Path("/setPreference")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setPreferenceWithId(@FormParam("preference") Preference preference) {

		logger.info("inside setpreference with id");
		String result = "Pref saved : test";
		//return Response.status(201).entity(result).build();
		return Response.ok().build();
	}*/


	/*@POST
	@Path("/setPreference/{profileId}")
	public Response setPreferenceWithId(
			@PathParam("profileId") String profileId,
			@FormParam("preference") String preference) {

		Gson gson = new Gson();
		Preference pref = gson.fromJson(preference, Preference.class);

		spopzService.upsertPreference(pref, preference);

		//logger.info("setPreferenceWithId="+ profileId +"; pref="+pref.getId());
		String str = "{'stored':'true'}";
		return Response.ok(str, MediaType.APPLICATION_JSON).build();
	}*/

	@POST
	@Path("/setPreference")
	public Response setPreference(
			@FormParam("preference") String preference) {

		Gson gson = new Gson();
		
		try{
			IntermediatePreference pref = gson.fromJson(preference, IntermediatePreference.class);
			spopzService.upsertPreference(pref, preference);
		}catch(Exception ex){
			Preference pref = gson.fromJson(preference, Preference.class);
			spopzService.upsertPreferencehack(pref, preference);
		}
		//spopzService.upsertPreference(pref, preference);
		
		/*Preference pref = gson.fromJson(preference, Preference.class);
		spopzService.upsertPreference(pref, preference);*/

		//logger.info("setPreferenceWithId="+ profileId +"; pref="+pref.getId());
		String str = "{'stored':'true'}";
		return Response.ok(str, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/getPreference/{profileId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PreferenceAsJson getPreference(@PathParam("profileId") String profileId) {
		PreferenceAsJson pref = spopzService.getPreference(profileId);
		if(pref != null)
			return pref;
		else{
			return new PreferenceAsJson();
		}
	}

	@GET
	@Path("/getPreference")
	public Response showMessageForEmptyPreference(){
		return Response.serverError().entity("profileId cannot be blank").build();
	}

	@GET
	@Path("/showReport")
	public String showReport(){

		List<PreferenceAsJson> listOfPreferences = spopzService.listPreferences();
		List<Profile> listOfProfiles = spopzService.listProfiles();
		List<User> listOfUsers = spopzService.listUsers();  
		
		return new StringBuffer("Preferences:::").append(new JSONSerializer().deepSerialize(listOfPreferences)).append("Profiles:::").
		append(new JSONSerializer().deepSerialize(listOfProfiles)).append("Users:::").
		append(new JSONSerializer().deepSerialize(listOfUsers)).toString();
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