package de.frittenburger.form;

import de.frittenburger.bo.ButtonBar;
import de.frittenburger.bo.EmailAddress;
import de.frittenburger.bo.Firstname;
import de.frittenburger.bo.Lastname;
import de.frittenburger.bo.Password;
import de.frittenburger.core.I18n;

public class UserAccount extends Form {

	
	public Firstname firstname;
	
	public Lastname lastname;
	
	public EmailAddress email;
	
	public Password password;

	@Override
	public ButtonBar getButtonbBar() {
		return new ButtonBar(ButtonBar.Cancel,ButtonBar.Save);
	}
	
	@Override
	public String getEntityName() {
		return I18n.tr("User Account");
	}
	
}
