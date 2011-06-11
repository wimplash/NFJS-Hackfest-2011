package com.blogspot.activeactive.chitchat.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.blogspot.activeactive.chitchat.dao.impl.UserDao;
import com.blogspot.activeactive.chitchat.domain.User;

@Path("/user")
public class UserResource {

	private static final UserDao userDao = new UserDao();

	@GET
	@Path("/id/{id}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public User getUser(@PathParam("id") final Integer id) {
		return userDao.getById(id);
	}

	@GET
	@Path("/nick/{nick}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public User getUser(@PathParam("nick") final String nick) {
		return userDao.getByNick(nick);
	}

	@GET
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public List<User> getAllUsers() {
		return userDao.getAll();
	}
}