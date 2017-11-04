package de.frittenburger.pages;

import java.util.Map;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.bo.Event;
import de.frittenburger.core.Database;
import de.frittenburger.html.HtmlTemplate;

public class PageCallback {

	private String pageKey;
	
	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}
	
	protected String getPageKey() {
		return pageKey;
	}
	
	
	public void Create(Database database,HtmlTemplate template) throws AdminPanelException {
		throw new AdminPanelException(0, "not implemented");
	}
	public void Read(Database database,HtmlTemplate template) throws AdminPanelException {
		throw new AdminPanelException(0, "not implemented");
	}
	public void Update(Database database,HtmlTemplate template) throws AdminPanelException {
		throw new AdminPanelException(0, "not implemented");
	}
	public void Delete(Database database,HtmlTemplate template) throws AdminPanelException {
		throw new AdminPanelException(0, "not implemented");	
	}
	public Event Post(Database database, Map<String, String[]> parameter) throws AdminPanelException {
		throw new AdminPanelException(0, "not implemented");			
	}

}
