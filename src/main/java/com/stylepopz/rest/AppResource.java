
package com.stylepopz.rest;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stylepopz.common.AppSingleton;
import com.stylepopz.model.Preferences;

@Path("/prefs")
public class AppResource {

	private static final Logger logger = LoggerFactory.getLogger(AppResource.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
	@Path("/getProfile/{profileId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Preferences getProfile(@PathParam("profileId") String profileId) {
		
		logger.info("fetching profile info="+profileId);
		
		// get the profile and retrieve the access_token
		
		String access_token = AppSingleton.INSTANCE.getDao().getAccessToken(profileId);
		
		// https://api.singly.com/services/twitter/tweets?access_token=my_token
		Map<String, String> postParams = new HashMap<String, String>();
		//postParams.put("access_token", AppSingleton.INSTANCE.getAccountStorage().getAccessToken(account));
		postParams.put("access_token", access_token);

		// delete the profile service
		String output = AppSingleton.INSTANCE.getSinglyService().doGetApiRequest("/services/facebook/self", postParams);
		logger.info("result="+output);
		
		Preferences pref = new Preferences();
		Map<String, String> size = new HashMap<String, String>();
		size.put("built", "M");
		size.put("shoe", "7");
		pref.setSize(size);
		pref.setId(profileId);
		return pref;
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
		postParams.put("access_token", AppSingleton.INSTANCE.getAccountStorage().getAccessToken(account));

		// delete the profile service
		AppSingleton.INSTANCE.getSinglyService().doPostApiRequest("/profiles", null, postParams);
		
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

	/*@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_XML)
	public Preferences getPrefs() {
		Preferences pref = new Preferences();
		Map<String, String> size = new HashMap<String, String>();
		size.put("built", "M");
		size.put("shoe", "7");
		pref.setSize(size);

		return pref;
	}*/


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