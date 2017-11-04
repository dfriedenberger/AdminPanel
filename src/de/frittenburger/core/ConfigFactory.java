package de.frittenburger.core;

import java.security.GeneralSecurityException;
import java.util.Map.Entry;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.bo.ConfigSection;
import de.frittenburger.bo.Input;
import de.frittenburger.bo.Parameter;
import de.frittenburger.form.Form;

public class ConfigFactory {

	
	public static ConfigSection createConfigSection(Form form, String id) throws AdminPanelException {
		    ConfigSection configSection = new ConfigSection(form.getClass().getSimpleName().toLowerCase(),id);
			
			Wrapper wrapper = new Wrapper(form);
			
			
			for(Entry<String, Input> e : wrapper.getInputFields())
			{
				Input input = e.getValue();
				Parameter pi = new Parameter(e.getKey());
				pi.addComment(e.getValue().getDescription());
				String value = input.getValue();
				if(input.mustProtect())
				{
					DataProtector protector = new DataProtector(AdminPanel.secretProvider);
					try {
						value = protector.protect(value);
					} catch (GeneralSecurityException e1) {
						throw new AdminPanelException(e1);
					}
					//Encrypt
				}
				
				if(input.mustEscape())
				{
					value = escape(value);
				}
					
				pi.setValue(value);
				configSection.add(pi);
			}
		
			return configSection;
		}


		


		public static void fillForm(ConfigSection section, Form form) throws AdminPanelException {
			
			Wrapper wrapper = new Wrapper(form);
			for(Parameter p : section.getParameter())
			{
					String key = p.getName();
					
					Input input = wrapper.getInputField(key);
					String value = p.getValue();
					
					if(input.mustEscape())
					{
						value = unescape(value);
					}
					
					if(input.mustProtect())
					{
						DataProtector protector = new DataProtector(AdminPanel.secretProvider);
						try {
							value = protector.unprotect(value);
						} catch (GeneralSecurityException e) {
							throw new AdminPanelException(e);
						}
						//Encrypt
					}
					
					input.setValue(value);
			}			
		}

		private static String escape(String val) {
			return "'"+val+"'";
		}

		private static String unescape(String value) {
			return value.substring(1,value.length() -1);
		}


}
