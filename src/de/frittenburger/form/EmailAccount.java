package de.frittenburger.form;

import de.frittenburger.bo.EmailProvider;
import de.frittenburger.bo.Password;
import de.frittenburger.bo.Server;
import de.frittenburger.bo.Username;
import de.frittenburger.core.I18n;

public class EmailAccount extends Form {

	public EmailProvider provider;

	public Username username;
	
	public Password password;
	
	public Server server;
		
	@Override
	public String getEntityName() {
		return I18n.tr("E-Mail Account");
	}

}
