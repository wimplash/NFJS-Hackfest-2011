package com.blogspot.activeactive.chitchat.resources;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blogspot.activeactive.chitchat.domain.User;

public class ITUserResource {

	private static HttpClient client;

	@BeforeClass
	public static void createClient() {
		client = new DefaultHttpClient();
	}

	@AfterClass
	public static void shutDownConnectionManager() {
		client.getConnectionManager().shutdown();
	}

	@Test
	public void userResourceShouldReturnXmlListForBaseUserUrl() throws Exception {
		final HttpGet get = new HttpGet("http://localhost:8080/resources/user");
		get.addHeader("Accept", MediaType.APPLICATION_XML);
		final HttpResponse rsp = client.execute(get);
		assertEquals(200, rsp.getStatusLine().getStatusCode());
		final JAXBContext ctx = JAXBContext.newInstance(Users.class);
		final Unmarshaller um = ctx.createUnmarshaller();
		final Object o = um.unmarshal(rsp.getEntity().getContent());
		assertNotNull(o);
		assertTrue(o instanceof Users);
		final Users u = (Users) o;
		assertEquals(2, u.getUser().size());
	}

	@Test
	public void userResourceShouldReturnJsonListForBaseUserUrl() throws Exception {
		final HttpGet get = new HttpGet("http://localhost:8080/resources/user");
		get.addHeader("Accept", MediaType.APPLICATION_JSON);
		final HttpResponse rsp = client.execute(get);
		assertEquals(200, rsp.getStatusLine().getStatusCode());
		final String content = IOUtils.toString(rsp.getEntity().getContent());
		assertNotNull(content);
		assertFalse(content.length() == 0);
		final JSONArray array = new JSONArray(new JSONTokener(content));
		assertEquals(2, array.length());
	}

	@Test
	public void userResourceShouldReturnXmlUserForUserUrlWithId() throws Exception {
		final HttpGet get = new HttpGet("http://localhost:8080/resources/user/id/1");
		get.addHeader("Accept", MediaType.APPLICATION_XML);
		final HttpResponse rsp = client.execute(get);
		assertEquals(200, rsp.getStatusLine().getStatusCode());
		final JAXBContext ctx = JAXBContext.newInstance(Users.class);
		final Unmarshaller um = ctx.createUnmarshaller();
		final Object o = um.unmarshal(rsp.getEntity().getContent());
		assertNotNull(o);
		assertTrue(o instanceof User);
		final User u = (User) o;
		assertEquals(new Integer(1), u.getId());
	}

	@Test
	public void userResourceShouldReturnJsonUserForUserUrlWithId() throws Exception {
		final HttpGet get = new HttpGet("http://localhost:8080/resources/user/id/1");
		get.addHeader("Accept", MediaType.APPLICATION_JSON);
		final HttpResponse rsp = client.execute(get);
		assertEquals(200, rsp.getStatusLine().getStatusCode());
		final String content = IOUtils.toString(rsp.getEntity().getContent());
		assertNotNull(content);
		assertFalse(content.length() == 0);
		final JSONObject user = new JSONObject(new JSONTokener(content));
		assertEquals(1, user.getInt("id"));
	}
}