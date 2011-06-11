package com.blogspot.activeactive.chitchat.resources;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.blogspot.activeactive.chitchat.domain.User;

@XmlRootElement
@XmlType
public class Users {
	private List<User> user;

	public List<User> getUser() {
		return user;
	}

	public void setUser(final List<User> user) {
		this.user = user;
	}
}
