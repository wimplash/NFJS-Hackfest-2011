package com.blogspot.activeactive.chitchat.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.blogspot.activeactive.chitchat.dao.impl.UserDao;
import com.blogspot.activeactive.chitchat.domain.User;
import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.NotFoundException;

@Path("/user")
public class UserResource {

	private static final UserDao userDao = new UserDao();

	@DELETE
	@Path("/id/{id}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public User deleteUserById(@PathParam("id") final Integer id) {
		getUserById(id);
		return userDao.deleteById(id);
	}

	@GET
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public List<User> getAllUsers() {
		return userDao.getAll();
	}

	@GET
	@Path("/id/{id}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public User getUserById(@PathParam("id") final Integer id) {
		final User user = userDao.getById(id);
		if (user == null) {
			throw new NotFoundException();
		}
		return user;
	}

	@GET
	@Path("/nick/{nick}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public User getUserByNick(@PathParam("nick") final String nick) {
		final User user = userDao.getByNick(nick);
		if (user == null) {
			throw new NotFoundException();
		}
		return user;
	}

	@PUT
	@Path("/id/{id}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public User putNewUserById(@PathParam("id") final Integer id) {
		final User user = userDao.create(id);
		if (user == null) {
			throw new ConflictException();
		}
		return user;
	}
}