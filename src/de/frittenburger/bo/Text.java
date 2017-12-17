package de.frittenburger.bo;

import de.frittenburger.core.I18n;

public class Text extends TextInput {

	@Override
	public String getDefaultDescription() {
		return I18n.tr("Text");
	}

}
