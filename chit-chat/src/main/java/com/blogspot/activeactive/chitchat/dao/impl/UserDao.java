package com.blogspot.activeactive.chitchat.dao.impl;

import static java.util.Collections.synchronizedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blogspot.activeactive.chitchat.domain.Name;
import com.blogspot.activeactive.chitchat.domain.User;

public class UserDao {

	private static final Map<Integer, User> users = synchronizedMap(new HashMap<Integer, User>());

	static {
		users.put(new Integer(1), createUser(1, "Ryan", "Ransford", "wimplash"));
		users.put(new Integer(2), createUser(2, "Gee-paw", "Hill"));
	}

	private static Name createName(final String first, final String last) {
		final Name n = new Name();
		n.setFirst(first);
		n.setLast(last);
		return n;
	}

	private static User createUser(final int id, final String firstName,
			final String lastName, final String nick) {
		final User u = new User();
		u.setId(new Integer(id));
		u.setName(createName(firstName, lastName));
		u.setNick(nick);
		return u;
	}

	private static User createUser(final int id, final String firstName,
			final String lastName) {
		return createUser(id, firstName, lastName, null);
	}

	public List<User> getAll() {
		return new ArrayList<User>(users.values());
	}

	public User getById(final Integer id) {
		return users.get(id);
	}

	public User getByNick(final String nick) {
		for (final User u : getAll()) {
			if (u.getNick() != null && u.getNick().equals(nick)) {
				return u;
			}
		}
		return null;
	}
}