package de.frittenburger.web;

import java.util.List;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.core.AdminPanel;
import de.frittenburger.core.Database;
import de.frittenburger.core.Page;
import de.frittenburger.html.HtmlTemplate;
import de.frittenburger.pages.PageCallback;

public class GetHandler extends BaseHandler {

	

	public void handle(String sessionId, String pathInfo, HtmlTemplate template) throws AdminPanelException {
		
		UserContext userContext = resolveContext(sessionId);
		
		
		Database database = new Database();
		
		
		String pageKey = getPage(pathInfo);
		int modus = getPageModi(pathInfo);
		if(pageKey == null)
		{
			pageKey = AdminPanel.selection().forGroup(userContext.getGroup()).getDefaultPage().getKey();
		}
		
		//get Page Definition
		Page page = AdminPanel.selection().forGroup(userContext.getGroup()).getPage(pageKey);

		if(modus == 0)
		{
			modus = AdminPanel.selection().forGroup(userContext.getGroup()).getDefaultModus(page);
		}
		
		//Add menu
		List<Page> pages = AdminPanel.selection().forGroup(userContext.getGroup()).getMenuPages();
		template.addMenu(pages);
		
		
		
		PageCallback pageCallBack = page.getCallback();
		
		switch(modus)
		{
		case Page.Create:
			pageCallBack.Create(database,template);			
			break;
		case Page.Read:
			pageCallBack.Read(database,template);
			break;
		case Page.Update:
			pageCallBack.Update(database,template);
			break;
		case Page.Delete:
			pageCallBack.Delete(database,template);
			break;
		}
		
		template.addFooter();
	}






	
}
