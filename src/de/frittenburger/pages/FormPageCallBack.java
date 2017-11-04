package de.frittenburger.pages;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.bo.Event;
import de.frittenburger.bo.Input;
import de.frittenburger.core.Database;
import de.frittenburger.core.I18n;
import de.frittenburger.core.Wrapper;
import de.frittenburger.form.Form;
import de.frittenburger.form.UserAccount;
import de.frittenburger.html.HtmlTemplate;

public class FormPageCallBack extends PageCallback {

	
	
	private Class<? extends Form> form;

	public FormPageCallBack(Class<? extends Form> form) {
		this.form = form;
	}

	@Override
	public void Create(Database database,HtmlTemplate template) throws AdminPanelException {
		
		//Create Form Instance			
		Form formInstance = database.createForm(form);
		template.addNotice(I18n.tr("Create {0}",formInstance.getEntityName()));
		template.addContentForm(formInstance,getPageKey()+"?Create="+UUID.randomUUID().toString());	
	}
	
	
	@Override
	public void Read(Database database,HtmlTemplate template) throws AdminPanelException {
		
		int cnt = database.countForm(form);
		
		if(cnt == 0)
		{
			Create(database,template);
			return;
		}
		
		
		
		List<Entry<String, Form>> data = database.getForms(form);
		
		
		//List		
		template.addNotice(data.get(0).getValue().getEntityName());
		
		template.addContentCollection(data,getPageKey());	

		
		
		
	}
	
	
	
	@Override
	public Event Post(Database database,Map<String, String[]> parameter) throws AdminPanelException {
	
		
		Form formInstance = database.createForm(form);

		String id = null;

		Wrapper wrapper = new Wrapper(formInstance);
		
		
		
        
		for(String name : parameter.keySet())
		{
			String value = parameter.get(name)[0];
			if(name.equals("Create"))
			{
				id = value;
				continue;
			}
			
			Input input = wrapper.getInputField(name);
			
			input.verify(value);
			input.setValue(value);
		
		}
		
		database.addForm(id, formInstance);
		
		if(formInstance instanceof UserAccount)
		{
			return new Event(Event.ChangeUser,id);
		}
		
		
		return new Event(Event.NoOperation);
	}
	
}
