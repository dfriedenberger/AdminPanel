package de.frittenburger.core;

import java.util.HashMap;
import java.util.Map;

public class RightsMatrix {

	Map<String,Integer> map = new HashMap<String,Integer>();
	
	public void add(Group g, Page p, int modus) {
		String key = key(g,p);
		int m = get(g,p);
		m |= modus;
		map.put(key,m); 
	}
	

	private String key(Group g, Page p) {
		return g.getName()+"_"+p.getKey();
	}

	public int get(Group g, Page p) {
		
		String key = key(g,p);
		if(!map.containsKey(key))
			return 0;
		return map.get(key);
	}

}
