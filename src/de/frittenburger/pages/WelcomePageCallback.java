package de.frittenburger.pages;

import java.util.Map;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.bo.Event;
import de.frittenburger.core.Database;
import de.frittenburger.core.I18n;
import de.frittenburger.core.Page;
import de.frittenburger.core.Select;
import de.frittenburger.form.Form;
import de.frittenburger.form.UserAccount;
import de.frittenburger.form.UserLogin;
import de.frittenburger.html.HtmlTemplate;

public class WelcomePageCallback extends PageCallback {

	@Override
	public void Read(Database database,HtmlTemplate template) throws AdminPanelException {

	
		int userCnt = database.countForm(UserAccount.class);
	
		if(userCnt == 0)
		{
			//Add Notice
			template.addNotice(I18n.tr("Looks like everything is installed and working. Now you can quickly configure your settings for a start. First create your user account."));
			//Add Button to Start
			template.addButton(I18n.tr("Let's get started"),Page.users);
		}
		else
		{
			Form formInstance = database.createForm(UserLogin.class);
			template.addContentForm(formInstance,getPageKey());	
		}
	
	}

	
	@Override
	public Event Post(Database database,Map<String, String[]> parameter) throws AdminPanelException {
	
		
		final String email = parameter.get("email")[0];
		final String password = parameter.get("password")[0];
		
		Select<UserAccount> accounts = new Select<UserAccount>(database.getForms(UserAccount.class));
		
		
		for(String id : accounts.ids())
		{
			UserAccount account = accounts.get(id);
			if(!account.email.getValue().equals(email)) continue;
			if(!account.password.getValue().equals(password)) continue;
			
			//Successful
			return new Event(Event.ChangeUser,id);		
			
		}
		
		throw new AdminPanelException(AdminPanelException.TAccessDenied, "Login Failed");
		

	
	}
	
	
	
}
