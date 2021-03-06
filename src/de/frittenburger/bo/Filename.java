package de.frittenburger.bo;

import java.io.File;

import de.frittenburger.core.I18n;

public class Filename extends TextInput {
	
	@Override
	public String getDefaultDescription() {
		return I18n.tr("File Name");
	}
	
	@Override
	public void verify(String value) throws AdminPanelException {
		super.verify(value);
		if(!new File(value).isFile())
			throw new AdminPanelException(AdminPanelException.TValidation,value + " is not a file");
		
	}


}
