package de.frittenburger.web;

import java.util.HashMap;
import java.util.Map;

import de.frittenburger.core.Group;
import de.frittenburger.core.Page;

public class BaseHandler {
    
	private static Map<String,UserContext> userContextMap = new HashMap<String,UserContext>();


	protected UserContext resolveContext(String sessionId) {
		
		
		if(userContextMap .containsKey(sessionId))
			return userContextMap.get(sessionId);
		
		
		UserContext usr = new UserContext();
		usr.setGroup(Group.Guest);
		usr.setUserId("Guest");
		
		userContextMap.put(sessionId,usr);
		return usr;

	}
	
	protected int getPageModi(String pathInfo) {
		int i = pathInfo.indexOf("/");
		if(i < 0)
			return 0;
		String func = pathInfo.substring(i+1);
		if(func.length() == 0)
			return 0;
		
		if(func.equals("read")) return Page.Read;
		if(func.equals("create")) return Page.Create;
		if(func.equals("update")) return Page.Update;

		return 0;
	}


	protected String getPage(String pathInfo) {
		
		int i = pathInfo.indexOf("/");
		if(i < 0)
		{
			if(pathInfo.length() == 0)
				return null;
			return pathInfo;
		}
		return pathInfo.substring(0,i);
	}
	
}
