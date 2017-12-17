package de.frittenburger.bo;

import java.io.File;

import de.frittenburger.core.I18n;

public class Directory extends TextInput {
	
	@Override
	public String getDefaultDescription() {
		return I18n.tr("Directory");
	}
	
	@Override
	public void verify(String value) throws AdminPanelException {
		super.verify(value);
		if(!new File(value).isDirectory())
			throw new AdminPanelException(AdminPanelException.TValidation,value + " is not a directory");
		
	}


}
