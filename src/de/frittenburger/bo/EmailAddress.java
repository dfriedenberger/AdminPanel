package de.frittenburger.bo;

import de.frittenburger.core.I18n;

public class EmailAddress extends TextInput {
	
	@Override
	public boolean mustEscape() {
		return false;
	}
	
	@Override
	public String getDescription() {
		return I18n.tr("E-Mail Address");
	}

	@Override
	public String getType() {
		return "email";
	}

	
	private static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
	
	@Override
	public void verify(String value) throws AdminPanelException {
		
		super.verify(value);
		
		if(!isValidEmailAddress(value)) throw new AdminPanelException(1,"Not valid email address");
		
	}
	
	 
}
