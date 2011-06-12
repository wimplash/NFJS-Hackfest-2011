package com.blogspot.activeactive.chitchat.domain;

import static com.sun.jersey.server.linking.Ref.Style.ABSOLUTE;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sun.jersey.server.linking.Ref;

@XmlRootElement
@XmlType
public class User {

	private Integer id;

	private Name name;

	private String nick;

	@Ref(value = "user/id/{id}", style = ABSOLUTE)
	private String url;

	@XmlAttribute(required = true)
	public Integer getId() {
		return id;
	}

	public Name getName() {
		return name;
	}

	public String getNick() {
		return nick;
	}

	public String getUrl() {
		return url;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setName(final Name name) {
		this.name = name;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return String.format("%1d: %2s (%3s)", getId(), getName(), getNick());
	}
}