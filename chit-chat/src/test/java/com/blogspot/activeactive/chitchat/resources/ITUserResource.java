package com.blogspot.activeactive.chitchat.resources;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.blogspot.activeactive.chitchat.domain.User;

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
	public void userResourceShouldReturnXmlListForBaseUserUrl() throws Exception {
		final Users u = helper.getPayloadFromXml("/user", Users.class);
		assertEquals(2, u.getUser().size());
	}

	@Test
	public void userResourceShouldReturnXmlUserForUserUrlWithId() throws Exception {
		final User u = helper.getPayloadFromXml("/user/id/1", User.class);
		assertEquals(new Integer(1), u.getId());
	}
}