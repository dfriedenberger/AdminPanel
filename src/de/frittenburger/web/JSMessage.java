package de.frittenburger.web;


public class JSMessage {

	private String state;
	private String message;
	private String redirect;

	public void setState(String state) {
		this.state = state;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;		
	}

	public String ToJson()
	{
		
		StringBuilder str = new StringBuilder();
		str.append("{");
		str.append(" \"state\" : \""+state+"\",");
		str.append(" \"message\" : \""+message+"\",");
		str.append(" \"redirect\" : \""+redirect+"\"");
		str.append("}");
		return str.toString();
	}

}
