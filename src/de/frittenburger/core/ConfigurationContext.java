package de.frittenburger.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationContext {

	List<Group> groups = new ArrayList<Group>();
	List<Page> pages = new ArrayList<Page>();
	public RightsMatrix matrix = new RightsMatrix();
	
	private String path = null;
	

	public Group addGroup(String name) {
		Group group = new Group();
		group.setName(name);
		groups.add(group);
	    return group;
	}
	

	public Page addPage(String key,String name) {

		Page page = new Page();
		page.setKey(key);
		page.setName(name);
		pages.add(page);
		
		return page;
		
	}

	public Selection newSelection() {
		return new Selection(this);
	}

	

	public void setPath(String path) {
		this.path = path;
		File p = new File(path);
		if(!p.exists())
			p.mkdir();
		
	}
	
	public String getPath() {
		return this.path;
	}

	

	

}
