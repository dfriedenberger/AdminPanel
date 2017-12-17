package de.frittenburger.form;

import de.frittenburger.bo.Directory;
import de.frittenburger.core.I18n;

public class DataDirectory extends Form {

	
	public Directory directory;
	
	
	@Override
	public String getEntityName() {
		return I18n.tr("Directory");
	}
}
