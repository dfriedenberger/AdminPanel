package de.frittenburger.core;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import de.frittenburger.bo.Input;
import de.frittenburger.form.Form;

public class Wrapper {

	private Form form;

	public Wrapper(Form form) {
		this.form = form;
	}

	public List<Entry<String,Input>> getInputFields() {
		List<Entry<String,Input>> inputs = new ArrayList<Entry<String,Input>>();
		
		Field[] fields = form.getClass().getFields();
		for(Field field : fields)
		{
			
			Object value = null;
			try {
				value = field.get(form);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			if(value instanceof Input)
			{
				field.getName();
				inputs.add(new AbstractMap.SimpleEntry<String,Input>(field.getName(),(Input)value));
			}
		}
		
		return inputs;
	}

	public Input getInputField(String name) {
		try {
			Field field = form.getClass().getField(name);
			Object ref = field.get(form);
			return (Input)ref;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
