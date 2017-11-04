package de.frittenburger.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.bo.ConfigSection;
import de.frittenburger.bo.Input;
import de.frittenburger.form.Form;

public class Database {

	public Form createForm(Class<? extends Form> formClass) throws AdminPanelException {
		
		Form form = null;
		try {
			form = formClass.newInstance();
			Field fields[] = formClass.getFields();
			for(Field f : fields)
			{
				if (Modifier.isStatic(f.getModifiers())) continue;
				
				Object val = f.getType().newInstance();
				f.set(form, val);
			}
			
		} catch (InstantiationException e) {
			throw new AdminPanelException(e);
		} catch (IllegalAccessException e) {
			throw new AdminPanelException(e);
		}
		return form;
	}
	
	public int countForm(Class<? extends Form> formClass) throws AdminPanelException {
		
		String name = formClass.getSimpleName().toLowerCase();
		ConfigFile file = new ConfigFile(AdminPanel.context().getPath()+"/"+name+".conf");
		if(file.exists())
			try {
				file.load();
			} catch (IOException e) {
				throw new AdminPanelException(e);
			}	
		return file.count();
	
	}

	public List<Entry<String,Form>> getForms(Class<? extends Form> formClass) throws AdminPanelException {
		
		
		List<Entry<String,Form>> forms = new ArrayList<Entry<String,Form>>();
		
		String name = formClass.getSimpleName().toLowerCase();
		ConfigFile file = new ConfigFile(AdminPanel.context().getPath()+"/"+name+".conf");
		if(file.exists())
			try {
				file.load();
			} catch (IOException e) {
				throw new AdminPanelException(e);
			}	
		
		for(ConfigSection section : file.getSections())
		{
			Form form = createForm(formClass);
			String id = section.getId();
			ConfigFactory.fillForm(section,form);
			forms.add(new AbstractMap.SimpleEntry<String,Form>(id,form));
		}
		
		return forms;
	
	}
	
	
	public void addForm(String id, Form form) throws AdminPanelException {
		
		
		String name = form.getClass().getSimpleName().toLowerCase();
		ConfigFile file = new ConfigFile(AdminPanel.context().getPath()+"/"+name+".conf");
		if(file.exists())
			try {
				file.load();
			} catch (IOException e2) {
				throw new AdminPanelException(e2);
			}
		
		System.out.println("Add "+id);
		
		Wrapper wrapper = new Wrapper(form);
		
		List<Entry<String,Input>> inputs = wrapper.getInputFields();
		for(Entry<String,Input> e : inputs)
		{
			System.out.println(e.getKey()+" = "+e.getValue().getValue());
		}
		
		ConfigSection configSection = ConfigFactory.createConfigSection(form,id);
		file.add(configSection);
		try {
			file.commit();
		} catch (IOException e1) {
			throw new AdminPanelException(e1);
		}
		
	}

	

}
