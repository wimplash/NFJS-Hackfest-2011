package com.blogspot.activeactive.chitchat.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static junit.framework.Assert.assertNotNull;
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
	public void postShouldAcceptXmlUser() throws Exception {
		final User u = helper.postXmlPayloadAndGetXmlPayload("/user",
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
						+ "<user>"
						+ "<name><first>Joe</first><last>O'Brien</last></name>"
						+ "<nick>objo</nick>" + "</user>", User.class);
		assertNotNull(u.getId());
		assertEquals("Joe", u.getName().getFirst());
		assertEquals("O'Brien", u.getName().getLast());
		assertEquals("objo", u.getNick());
	}

	@Test
	public void postShouldStoreXmlUser() throws Exception {
		helper.postXmlPayloadAndGetXmlPayload("/user",
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<user>"
				+ "<name><first>Christopher</first><last>Judd</last></name>"
				+ "<nick>javajudd</nick>" + "</user>", User.class);
		final User u = helper.getPayloadFromXml("/user/nick/javajudd", User.class);
		assertNotNull(u.getId());
		assertEquals("Christopher", u.getName().getFirst());
		assertEquals("Judd", u.getName().getLast());
		assertEquals("javajudd", u.getNick());
	}

	@Test
	public void postShouldAcceptJsonUser() throws Exception {
		final JSONObject u = helper.postJsonPayloadAndGetJsonPayload("/user",
				"{\"name\":{\"first\":\"Mattimeo\",\"last\":\"Mouse\"},\"nick\":\"mattimeo\"}");
		assertTrue(u.has("id"));
		assertEquals("Mattimeo", u.getJSONObject("name").get("first"));
		assertEquals("Mouse", u.getJSONObject("name").get("last"));
		assertEquals("mattimeo", u.get("nick"));
	}
	
	@Test
	public void postShouldStoreJsonUser() throws Exception {
		helper.postJsonPayloadAndGetJsonPayload("/user",
				"{\"name\":{\"first\":\"Ralph\",\"last\":\"Mouse\"},\"nick\":\"ralphie\"}");
		final JSONObject u = helper.getPayloadFromJson("/user/nick/ralphie");
		assertTrue(u.has("id"));
		assertEquals("Ralph", u.getJSONObject("name").get("first"));
		assertEquals("Mouse", u.getJSONObject("name").get("last"));
		assertEquals("ralphie", u.get("nick"));
	}

	@Test
	public void deleteByNickShouldRemoveUser() throws Exception {
		helper.postJsonPayloadAndGetJsonPayload("/user",
				"{\"name\":{\"first\":\"Delete\",\"last\":\"Me\"},\"nick\":\"delete_me\"}");
		helper.deleteResponse("/user/nick/delete_me");
		final HttpResponse rsp = helper.getResponse("/user/nick/delete_me", APPLICATION_XML);
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}

	@Test
	public void deleteByInvalidNickShouldFailWith404() throws Exception {
		final HttpResponse rsp = helper.deleteResponse("/user/nick/delete_me");
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}

	@Test
	public void getByInvalidNickShouldFailWith404() throws Exception {
		final HttpResponse rsp = helper.getResponse("/user/nick/invalid", APPLICATION_XML);
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}

	@Test
	public void deleteByIdShouldRemoveUser() throws Exception {
		final JSONObject u = helper.postJsonPayloadAndGetJsonPayload("/user",
				"{\"name\":{\"first\":\"Delete\",\"last\":\"Me\"},\"nick\":\"delete_me\"}");
		final Integer id = u.getInt("id");
		helper.deleteResponse("/user/id/" + id);
		final HttpResponse rsp = helper.getResponse("/user/id/" + id, APPLICATION_XML);
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}

	@Test
	public void deleteByInvalidIdShouldFailWith404() throws Exception {
		final HttpResponse rsp = helper.deleteResponse("/user/id/99");
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}

	@Test
	public void getByInvalidIdShouldFailWith404() throws Exception {
		final HttpResponse rsp = helper.getResponse("/user/id/99", APPLICATION_XML);
		assertEquals(404, rsp.getStatusLine().getStatusCode());
	}
}