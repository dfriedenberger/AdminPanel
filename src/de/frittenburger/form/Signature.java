package de.frittenburger.form;

import de.frittenburger.bo.Filename;
import de.frittenburger.bo.Text;
import de.frittenburger.bo.Name;
import de.frittenburger.bo.Password;
import de.frittenburger.core.I18n;

public class Signature extends Form {

	@Name("Key store filename")
    public Filename keystorepath;
	
    @Name("Keystore password")
	public Password keystorepassword;
	
    @Name("Private key password")
	public Password privatekeypassword;

    @Name("Location")
	public Text location;
	
    @Name("Reason")
	public Text reason;

	
	@Override
	public String getEntityName() {
		return I18n.tr("Signature");
	}


}
