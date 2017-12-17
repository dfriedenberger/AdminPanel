package de.frittenburger.core;

import de.frittenburger.form.Form;
import de.frittenburger.pages.FormPageCallBack;
import de.frittenburger.pages.PageCallback;

public class Page {

	
	public static final int Create  = 1;
	public static final int Read    = 2;
	public static final int Update  = 4;
	public static final int Delete  = 8;
	public static final int NoMenu = 16;

	public static final int CRUD    = Create | Read | Update | Delete;
	
	public static String users = "users";
	public static String welcome = "welcome";

	
	private String name;
	private String key;
	private PageCallback pageCallback;

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public <T extends Form> void setForm(Class<T> form) {
		setCallback(new FormPageCallBack<T>(form,false));
	}
	public <T extends Form> void setSingletonForm(Class<T> form) {
		setCallback(new FormPageCallBack<T>(form,true));
	}
	
	public void setCallback(PageCallback pageCallback) {
		this.pageCallback = pageCallback;
		this.pageCallback.setPageKey(key);
	}

	public PageCallback getCallback() {
		return pageCallback;
	}





}
