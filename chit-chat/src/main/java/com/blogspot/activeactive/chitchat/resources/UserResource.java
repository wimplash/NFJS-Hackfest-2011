package com.blogspot.activeactive.chitchat.resources;

import static java.util.Collections.synchronizedList;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.blogspot.activeactive.chitchat.domain.Name;
import com.blogspot.activeactive.chitchat.domain.User;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/user")
public class UserResource {

	private static final List<User> users = synchronizedList(new ArrayList<User>());
	static {
		users.add(createUser(1, "Ryan", "Ransford"));
		users.add(createUser(2, "Gee-paw", "Hill"));
	}

	private static User createUser(final int id, final String firstName,
			final String lastName) {
		final User u = new User();
		u.setId(id);
		u.setName(createName(firstName, lastName));
		return u;
	}

	private static Name createName(final String first, final String last) {
		final Name n = new Name();
		n.setFirst(first);
		n.setLast(last);
		return n;
	}

	@GET
	@Produces(APPLICATION_XML)
	public List<User> getAllUsers_Xml() {
		return users;
	}
}