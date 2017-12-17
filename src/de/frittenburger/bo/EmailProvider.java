package de.frittenburger.bo;

import de.frittenburger.core.I18n;

public class EmailProvider extends SelectInput {

	@Override
	public String getDefaultDescription() {
		return I18n.tr("Provider");
	}
	
	@Override
	public boolean mustEscape() {
		return false;
	}
	
	@Override
	public String[] getValues() {
		return new String[]{ "imap" , "pop3" };
	}

	



}
