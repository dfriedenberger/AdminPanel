package de.frittenburger.form;

import de.frittenburger.bo.ButtonBar;
import de.frittenburger.bo.EmailAddress;
import de.frittenburger.bo.Password;
import de.frittenburger.core.I18n;

public class UserLogin extends Form {

	public EmailAddress email;
	
	public Password password;

	@Override
	public ButtonBar getButtonbBar() {
		return new ButtonBar(ButtonBar.Login);
	}
	
	@Override
	public String getEntityName() {
		return I18n.tr("Login");
	}
	
}
