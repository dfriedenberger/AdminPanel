package de.frittenburger.form;

import de.frittenburger.bo.ButtonBar;
import de.frittenburger.bo.EmailAddress;
import de.frittenburger.bo.Password;
import de.frittenburger.bo.Port;
import de.frittenburger.bo.Server;
import de.frittenburger.core.I18n;

public class EmailAccount extends Form {

	
	public EmailAddress email;
	
	public Password password;
	
	public Server server;
	
	public Port port;
	
	
	
	@Override
	public ButtonBar getButtonbBar() {
		return new ButtonBar(ButtonBar.Cancel,ButtonBar.Save);
	}

	@Override
	public String getEntityName() {
		return I18n.tr("E-Mail Account");
	}

}
