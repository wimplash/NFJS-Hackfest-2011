package com.blogspot.activeactive.chitchat.resources;

import static java.util.Collections.synchronizedList;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_HTML;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.blogspot.activeactive.chitchat.domain.User;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/user")
public class UserResource {

	private static final List<User> users = synchronizedList(new ArrayList<User>());
	static {
		users.add(new User(1, "Ryan", "Ransford"));
		users.add(new User(2, "Gee-paw", "Hill"));
	}

	@GET
	@Produces(APPLICATION_XML)
	public String getAllUsers_Xml() {
		final StringBuffer buf = new StringBuffer();
		final ListIterator<User> iter = users.listIterator();
		while (iter.hasNext()) {
			if (!iter.hasPrevious()) {
				buf.append("<users>");
			}
			buf.append(iter.next().toXml());
			if (!iter.hasNext()) {
				buf.append("</users>");
			}
		}
		return buf.toString();
	}

	@GET
	@Produces(TEXT_HTML)
	public String getAllUsers_Plain() {
		final StringBuffer buf = new StringBuffer();
		final ListIterator<User> iter = users.listIterator();
		while (iter.hasNext()) {
			if (!iter.hasPrevious()) {
				buf.append("<h1>Users</h1><ul>");
			}
			buf.append("<li>").append(iter.next()).append("</li>");
			if (!iter.hasNext()) {
				buf.append("</ul>");
			}
		}
		return buf.toString();
	}
}