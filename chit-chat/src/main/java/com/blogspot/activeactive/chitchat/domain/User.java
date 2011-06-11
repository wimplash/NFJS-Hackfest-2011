package com.blogspot.activeactive.chitchat.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class User {

	private Integer id;

	private Name name;

	@XmlAttribute(required=true)
	public Integer getId() {
		return id;
	}

	public Name getName() {
		return name;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setName(final Name name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("%1d: %2s", getId(), getName());
	}
}