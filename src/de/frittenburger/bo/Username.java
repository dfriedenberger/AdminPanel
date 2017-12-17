package de.frittenburger.bo;

import de.frittenburger.core.I18n;

public class Username extends TextInput {
	
	@Override
	public boolean mustEscape() {
		return false;
	}
	
	@Override
	public String getDefaultDescription() {
		return I18n.tr("User name");
	}


	 
}
