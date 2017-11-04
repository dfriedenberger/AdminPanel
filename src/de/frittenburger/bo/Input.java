package de.frittenburger.bo;

public interface Input {

	String getDescription();

	String getType();

	void setValue(String value);

	String getValue();

	boolean mustProtect();

	boolean mustEscape();

	void verify(String value) throws AdminPanelException;

}
