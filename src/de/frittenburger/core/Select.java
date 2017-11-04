package de.frittenburger.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.frittenburger.form.Form;
public  class Select<T extends Form> {


	private List<String> idList = new ArrayList<String>();
	private Map<String,Form> formMap = new HashMap<String,Form>();

	public Select(List<Entry<String, Form>> forms) {

		for(Entry <String, Form> e : forms)
		{
			idList.add(e.getKey());
			formMap.put(e.getKey(),e.getValue());
		}
	
	}

	public List<String> ids() {
		return idList;
	}

	@SuppressWarnings("unchecked")
	public T get(String id) {
		return (T) formMap.get(id);
	}

	

}
