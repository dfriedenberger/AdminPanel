package de.frittenburger.bo;

public class Event {

	public static final String ChangeUser = "ChangeUser";

	public static final String NoOperation = "NoOperation";

	private String type;
	private String id;

	public Event(String type, String id) {
		this.type = type;
		this.id = id;
	}

	public Event(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}


}
