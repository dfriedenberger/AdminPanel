package de.frittenburger.bo;

import java.util.HashSet;
import java.util.Set;

import de.frittenburger.core.I18n;

public class ButtonBar {
	
	public static final String Cancel   = I18n.marktr("Cancel");
	public static final String Save     = I18n.marktr("Save");
	public static final String Login    = I18n.marktr("Login");

	private Set<String> buttons;
	public ButtonBar(String ... btns) {
		buttons = new HashSet<String>();
		for(String btn : btns)
			buttons.add(btn);
	}
	
	public boolean hasButton(String btn) {
		return buttons.contains(btn);
	}
	
	


}
