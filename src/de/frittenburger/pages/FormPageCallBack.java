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

public class FormPageCallBack<T extends Form> extends PageCallback {

	
	
	private final Class<T> form;
	private final boolean singleton;

	public FormPageCallBack(Class<T> form, boolean singleton) {
		this.form = form;
		this.singleton = singleton;
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
		
		
		
		List<Entry<String,T>> data = database.getForms(form);
		
		
		//List		
		template.addNotice(data.get(0).getValue().getEntityName());
			
		template.addContentCollection(data,getPageKey(),(!singleton) || (cnt == 0));	

		
	
		
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
		
		if(singleton)
		{
			switch(database.countForm(form))
			{
			case 0:
				break;
			case 1:
				if(database.getForms(form).iterator().next().getKey().equals(id)) break; //Update of ID
			default:
				throw new AdminPanelException(AdminPanelException.TSystem,"singleton violation");
			}
		}
		
		database.addForm(id, formInstance);
		
		if(formInstance instanceof UserAccount)
		{
			return new Event(Event.ChangeUser,id);
		}
		return new Event(Event.NoOperation);
	}
	
}
