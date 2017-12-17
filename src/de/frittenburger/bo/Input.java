package de.frittenburger.bo;

import de.frittenburger.core.I18n;

public abstract class Input {

	private String description;


	public void setDescription(String value)
	{
		description = value;
	}

	
	public String getDescription()
	{
		if(description != null)
			return I18n.translate(description);
		return getDefaultDescription();
	}

	public abstract String getDefaultDescription();

	
	public abstract String getType();

	public abstract void setValue(String value);

	public abstract String getValue();

	public abstract boolean mustProtect();

	public abstract boolean mustEscape();

	public abstract void verify(String value) throws AdminPanelException;


}
