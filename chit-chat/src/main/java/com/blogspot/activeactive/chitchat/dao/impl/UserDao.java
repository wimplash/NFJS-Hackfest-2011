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
		createUser(1, "Ryan", "Ransford", "wimplash");
		createUser(2, "Gee-paw", "Hill");
	}

	private static Name createName(final String first, final String last) {
		if (hasText(first) || hasText(last)) {
			final Name n = new Name();
			if (hasText(first)) {
				n.setFirst(first);
			}
			if (hasText(last)) {
				n.setLast(last);
			}
			return n;
		} else {
			return null;
		}
	}

	private static User createUser(final int id) {
		return createUser(id, null, null);
	}

	private static User createUser(final int id, final String firstName,
			final String lastName) {
		return createUser(id, firstName, lastName, null);
	}

	private static User createUser(final int id, final String firstName,
			final String lastName, final String nick) {
		final User u = new User();
		u.setId(new Integer(id));
		if (hasText(firstName) || hasText(lastName)) {
			u.setName(createName(firstName, lastName));
		}
		if (hasText(nick)) {
			u.setNick(nick);
		}
		users.put(new Integer(id), u);
		return u;
	}

	private static boolean hasText(final String text) {
		return text != null && (!"".equals(text.trim()));
	}

	public User create(final Integer id) {
		if (users.containsKey(id)) {
			return null;
		}
		return createUser(id);
	}

	public User deleteById(final Integer id) {
		return users.remove(id);
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