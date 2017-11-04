package de.frittenburger.form;

import de.frittenburger.bo.ButtonBar;
import de.frittenburger.bo.Filename;
import de.frittenburger.core.I18n;

public class DataFile extends Form {

	
	public Filename filename;
	
	
	@Override
	public ButtonBar getButtonbBar() {
		return new ButtonBar(ButtonBar.Cancel,ButtonBar.Save);
	}
	
	@Override
	public String getEntityName() {
		return I18n.tr("Data File");
	}
}
