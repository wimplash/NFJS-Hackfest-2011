package com.blogspot.activeactive.chitchat.domain;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class Name {

	private String first;

	private String last;

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public void setFirst(final String first) {
		this.first = first;
	}

	public void setLast(final String last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return String.format("%s1 %s2", getFirst(), getLast());
	}
}