package de.frittenburger.form;

import de.frittenburger.bo.Filename;
import de.frittenburger.core.I18n;

public class DataFile extends Form {

	
	public Filename filename;
	
	
	@Override
	public String getEntityName() {
		return I18n.tr("Data File");
	}
}
