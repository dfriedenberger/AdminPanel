package de.frittenburger.web;


import java.util.Map;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.bo.Event;
import de.frittenburger.core.AdminPanel;
import de.frittenburger.core.Database;
import de.frittenburger.core.Group;
import de.frittenburger.core.Page;
import de.frittenburger.pages.PageCallback;

public class PostHandler extends BaseHandler {

	public String handle(String sessionId, String pathInfo, Map<String, String[]> parameter) throws AdminPanelException {
	
		UserContext userContext = resolveContext(sessionId);

		String pageKey = getPage(pathInfo);		
		
		
		Page page = AdminPanel.selection().forGroup(userContext.getGroup()).getPage(pageKey);

		
		
		Database database = new Database();
		
		
		PageCallback pageCallback = page.getCallback();
		Event evnt = pageCallback.Post(database,parameter);
		

		if(evnt.getType().equals(Event.ChangeUser))
		{
			userContext.setUserId(evnt.getId());
			userContext.setGroup(Group.User);
		}
		
		return AdminPanel.selection().forGroup(userContext.getGroup()).getDefaultPage().getKey();

	}

	

}
