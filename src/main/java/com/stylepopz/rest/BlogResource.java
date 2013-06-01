
package com.stylepopz.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.singly.util.HttpClientServiceImpl;

import diffboat.api.DiffbotAPI;
import diffboat.model.Article;

@Path("/blog")
@Produces({MediaType.APPLICATION_JSON})
@Component
public class BlogResource {

	private static final Logger logger = LoggerFactory.getLogger(BlogResource.class);
	private static String diffBotToken = "51af0040f4b45b4fd731606c13459c383";

	@Autowired
	HttpClientServiceImpl httpClient;


	@GET
	@Path("/getArticle/{uri}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Article getArticle(@PathParam("uri") String uri) {

		DiffbotAPI api = new DiffbotAPI(diffBotToken);
		Article article = null;
		try {
			article = api.article()
					.extractFrom(uri)
					.analyze();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return article;

	}

	@GET
	@Path("/getFrontPage/{uri}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public String getFrontPage(@PathParam("uri") String uri) throws UnsupportedEncodingException {

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://www.diffbot.com/api/frontpage?token="+diffBotToken+"&url="+URLEncoder.encode("http://"+uri, "ISO-8859-1"));
		request.addHeader("accept", "application/json");

		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			StringBuffer sb = new StringBuffer();
			String output;
			logger.info("Output from Server .... \n");
			while (br != null && ((output = br.readLine()) != null)) {
				sb.append(output);
			}
			return sb.toString();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "{'none':'na'}";
	}

	@GET
	@Path("/getProfile")
	public Response showMessageForEmptyProfile(){
		return Response.serverError().entity("profileId cannot be blank!!").build();
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

} 