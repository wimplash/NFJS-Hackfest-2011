package com.blogspot.activeactive.chitchat.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.blogspot.activeactive.chitchat.domain.User;
import com.blogspot.activeactive.chitchat.domain.Users;

public class ITUserResource {

	private RestResourceTestHelper helper = new RestResourceTestHelper();

	@Test
	public void userResourceShouldReturnJsonListForBaseUserUrl() throws Exception {
		final JSONArray array = helper.getPayloadArrayFromJson("/user");
		assertEquals(2, array.length());
	}

	@Test
	public void userResourceShouldReturnJsonUserForUserUrlWithId() throws Exception {
		final JSONObject user = helper.getPayloadFromJson("/user/id/1");
		assertEquals(1, user.getInt("id"));
	}

	@Test
	public void userResourceShouldReturnJsonUserForUserUrlWithNick() throws Exception {
		final JSONObject user = helper.getPayloadFromJson("/user/nick/wimplash");
		assertEquals(1, user.getInt("id"));
	}

	@Test
	public void userResourceShouldReturnXmlListForBaseUserUrl() throws Exception {
		final Users u = helper.getPayloadFromXml("/user", Users.class);
		assertEquals(2, u.getUser().size());
	}

	@Test
	public void userResourceShouldReturnXmlUserForUserUrlWithId() throws Exception {
		final User u = helper.getPayloadFromXml("/user/id/1", User.class);
		assertEquals(new Integer(1), u.getId());
	}

	@Test
	public void userResourceShouldReturnXmlUserForUserUrlWithNick() throws Exception {
		final User u = helper.getPayloadFromXml("/user/nick/wimplash", User.class);
		assertEquals(new Integer(1), u.getId());
	}

	@Test
	public void xmlUserInstanceShouldContainUrlAttribute() throws Exception {
		final User u = helper.getPayloadFromXml("/user/id/1", User.class);
		assertEquals(helper.getBaseUrl() + "/user/id/1", u.getUrl());
	}

	@Test
	public void jsonUserInstanceShouldContainUrlAttribute() throws Exception {
		final JSONObject u = helper.getPayloadFromJson("/user/id/1");
		assertTrue(u.has("url"));
		assertEquals(helper.getBaseUrl() + "/user/id/1", u.get("url"));
	}

	@Test
	public void putShouldStoreEmptyUser() throws Exception {
		helper.putAndGetJsonResponse("/user/id/97");
		final User u = helper.getPayloadFromXml("/user/id/97", User.class);
		assertNotNull(u);
	}

	@Test
	public void putShouldCreateAndReturnAnEmptyXmlUser() throws Exception {
		final User u = helper.putAndGetXmlResponse("/user/id/98", User.class);
		assertEquals(new Integer(98), u.getId());
		assertNull(u.getName());
		assertNull(u.getNick());
	}
	
	@Test
	public void putShouldCreateAndReturnAnEmptyJsonUser() throws Exception {
		final JSONObject u = helper.putAndGetJsonResponse("/user/id/99");
		assertEquals(99, u.getInt("id"));
		assertTrue(u.isNull("name"));
		assertTrue(u.isNull("nick"));
	}

	@Test
	public void putShouldFailWith409WhenIdAlreadyExists() throws Exception {
		final HttpResponse rsp = helper.putResponse("/user/id/1", APPLICATION_XML);
		assertEquals(409, rsp.getStatusLine().getStatusCode());
	}

	@Test
	public void getByInvalidNickShouldFailWith404() throws Exception {
		final HttpResponse rsp = helper.getResponse("/user/nick/invalid", APPLICATION_XML);
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}

	@Test
	public void deleteByIdShouldRemoveUser() throws Exception {
		helper.putAndGetJsonResponse("/user/id/999");
		helper.deleteResponse("/user/id/999");
		final HttpResponse rsp = helper.getResponse("/user/id/999", APPLICATION_XML);
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}

	@Test
	public void deleteByInvalidIdShouldFailWith404() throws Exception {
		final HttpResponse rsp = helper.deleteResponse("/user/id/999");
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}

	@Test
	public void getByInvalidIdShouldFailWith404() throws Exception {
		final HttpResponse rsp = helper.getResponse("/user/id/999", APPLICATION_XML);
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}
}