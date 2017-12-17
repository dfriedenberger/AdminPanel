package de.frittenburger.form;

import de.frittenburger.bo.EmailAddress;
import de.frittenburger.bo.Name;
import de.frittenburger.bo.Password;
import de.frittenburger.bo.Text;
import de.frittenburger.core.I18n;

public class UserAccount extends Form {

	@Name("First name")
	public Text firstname;
	
	@Name("First name")
	public Text lastname;
	
	public EmailAddress email;
	
	public Password password;

	@Override
	public String getEntityName() {
		return I18n.tr("User Account");
	}
	
}
