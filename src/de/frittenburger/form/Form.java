package de.frittenburger.form;

import de.frittenburger.bo.ButtonBar;

public abstract class Form {

	
	public abstract String getEntityName();

	public ButtonBar getButtonbBar()
	{
		return new ButtonBar(ButtonBar.Cancel,ButtonBar.Save);
	}
}
