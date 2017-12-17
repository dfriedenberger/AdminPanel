package de.frittenburger.bo;

public abstract class SelectInput extends Input {

	private String value;
	
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getType() {
		return "select";
	}
	
	public abstract String[] getValues();

	
	@Override
	public boolean mustProtect() {
		return false;
	}

	@Override
	public boolean mustEscape() {
		return true;
	}
	
	@Override
	public void verify(String value) throws AdminPanelException {
		if(value == null) throw new AdminPanelException(AdminPanelException.TSystem, "value is null");
		if(value.isEmpty()) throw new AdminPanelException(AdminPanelException.TValidation, "empty value not allowed");
		
		String[] values = getValues();
		for(String v : values)
		{
			if(v.equals(value))
				return; //valid
		}
		throw new AdminPanelException(AdminPanelException.TValidation, "value not in list");
	}


}
