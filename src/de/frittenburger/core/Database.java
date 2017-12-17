package de.frittenburger.core;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.bo.ConfigSection;
import de.frittenburger.bo.Input;
import de.frittenburger.bo.Name;
import de.frittenburger.form.Form;

public class Database {

	
	
	//private Logger logger = AdminPanel.getLogger(this.getClass().getName());
	
	public <T extends Form> T createForm(Class<T> formClass) throws AdminPanelException {
		
		T form = null;
		try {
			form = formClass.newInstance();
			Field fields[] = formClass.getFields();
			for(Field f : fields)
			{
				
				if (Modifier.isStatic(f.getModifiers())) continue;
				
				Object val = f.getType().newInstance();
				Name n = f.getAnnotation(Name.class);
				if((n != null) && (val instanceof Input))
				{
					((Input)val).setDescription(n.value());
				}
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

	public <T extends Form> List<Entry<String,T>> getForms(Class<T> formClass) throws AdminPanelException {
		
		
		List<Entry<String,T>> forms = new ArrayList<Entry<String,T>>();
		
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
			T form = createForm(formClass);
			String id = section.getId();
			ConfigFactory.fillForm(section,form);
			forms.add(new AbstractMap.SimpleEntry<String,T>(id,form));
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
		
		
		ConfigSection configSection = ConfigFactory.createConfigSection(form,id);
		file.add(configSection);
		try {
			file.commit();
		} catch (IOException e1) {
			throw new AdminPanelException(e1);
		}
		
	}

	

}
