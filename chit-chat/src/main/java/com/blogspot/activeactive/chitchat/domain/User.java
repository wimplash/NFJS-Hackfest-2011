package com.blogspot.activeactive.chitchat.domain;

public class User {

	public final int id;
	public final String firstName;
	public final String lastName;

	public User(final int id, final String firstName, final String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format("%1d: %2s %3s", id, firstName, lastName);
	}

	public String toXml() {
		final StringBuffer buf = new StringBuffer();
		buf.append("<user id=\"").append(id).append("\">");
		buf.append("<name>");
		buf.append("<first>").append(firstName).append("</first>");
		buf.append("<last>").append(lastName).append("</last>");
		buf.append("</user>");
		return buf.toString();
	}
}