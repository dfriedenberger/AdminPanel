package de.frittenburger.bo;

import de.frittenburger.core.I18n;

public class Password extends TextInput {

	
	@Override
	public String getDefaultDescription() {
		return I18n.tr("Password");
	}
	
	@Override
	public String getType() {
		return "password";
	}

	@Override
	public boolean mustEscape() {
		return false;
	}
	
	@Override
	public boolean mustProtect() {
		return true;
	}

	@Override
	public void verify(String value) throws AdminPanelException {
		
		if(value == null) throw new AdminPanelException(AdminPanelException.TSystem,"Password is null");
		
	}

	
}
