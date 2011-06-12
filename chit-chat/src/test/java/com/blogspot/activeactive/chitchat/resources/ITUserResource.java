package com.blogspot.activeactive.chitchat.resources;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

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
}