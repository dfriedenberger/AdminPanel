package de.frittenburger.core;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import de.frittenburger.bo.AdminPanelException;

public class Selection {

	private ConfigurationContext configurationContext;
	private List<Group> groupSelection = new ArrayList<Group>();
	private List<Page> pageSelection = new ArrayList<Page>();

	public Selection(ConfigurationContext configurationContext) {
		this.configurationContext = configurationContext;
	}

	public Selection forGroup(String name) {
		
		for(Group g :  configurationContext.groups)
			if(g.getName().equals(name)) 
				groupSelection.add(g);
		return this;
	}

	public Selection forPage(String key) {
		for(Page p :  configurationContext.pages)
			if(p.getKey().equals(key)) 
				pageSelection.add(p);
		return this;		
	}

	private Selection set(int modus) {
		
		for(Group g : groupSelection)
			for(Page p : pageSelection)
				configurationContext.matrix.add(g,p,modus);
		return this;
	}
	
	public Selection allowCreate() {
		return set(Page.Create);
	}
	
	public Selection allowRead() {
		return set(Page.Read);
	}

	public Selection showNotInMenu() {
		return set(Page.NoMenu);
	}
	
	public Selection allowAll() {
		return set(Page.Create | Page.Read | Page.Update | Page.Delete);		
	}

	public List<Page> getMenuPages() throws AdminPanelException {
		List<Page> pages = new ArrayList<Page>();

		for(Page p :  configurationContext.pages)
		{
			if(!isSet(p,Page.CRUD)) continue;
			if(isSet(p,Page.NoMenu)) continue;
			pages.add(p);
		}
		return pages;
	}

	public Page getDefaultPage() throws AdminPanelException {
	
		for(Page p :  configurationContext.pages)
		{
			if(!isSet(p,Page.CRUD)) continue;
				return p;
		}
		throw new NoSuchElementException("defaultpage");
	}
	
	public Page getPage(String key) throws AdminPanelException {
		
		for(Page p :  configurationContext.pages)
		{
			if(!p.getKey().equals(key)) continue;
			if(!isSet(p,Page.CRUD)) continue;
			return p;
		}
		throw new AdminPanelException(AdminPanelException.TSystem,"no such page "+key);
	}
	
	

    private boolean isSet(Page p,int modus) throws AdminPanelException {
		
		if(groupSelection.size() != 1)
			throw new AdminPanelException(0, "select excact one group");
		
		return (configurationContext.matrix.get(groupSelection.get(0),p) & modus) != 0;
	}
	
	public int getDefaultModus(Page page) {
		int modus = Page.CRUD;
		for(Group g : groupSelection)
		{
			int m = configurationContext.matrix.get(g,page);
			modus &= m;
		}
		
		if((modus & Page.Read) == Page.Read) return Page.Read;
		if((modus & Page.Create) == Page.Create) return Page.Create;
	
		return 0;
	}

	


	

	

	

	
}
