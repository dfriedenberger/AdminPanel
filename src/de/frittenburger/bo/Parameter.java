package de.frittenburger.bo;

import java.util.ArrayList;
import java.util.List;

public class Parameter {

	private String name;
	private List<String> comments = new ArrayList<String>();
	private String value;

	public Parameter(String name) {
		this.name = name;
	}

	public void addComment(String text) {
		for(String l : text.split("\n"))
			comments.add(l.trim());
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getComments() {
		return comments;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
