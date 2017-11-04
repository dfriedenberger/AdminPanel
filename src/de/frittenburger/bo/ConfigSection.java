package de.frittenburger.bo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ConfigSection {

	
	public static final String TAB = "    ";
	private List<Parameter> parameter = new ArrayList<Parameter>();
	private List<ConfigSection> childs = new ArrayList<ConfigSection>();
	private String name;
	private String id;

	public ConfigSection( String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void add(Parameter p) {
		parameter.add(p);		
	}

	public void writeTo(String tab, PrintWriter out) {

		
		out.println(tab + name+" "+id+" {");
		out.println();
		for(int i = 0;i < parameter.size();i++)
		{
			Parameter p = parameter.get(i);
			for(String l : p.getComments())
				out.println(tab + TAB + "#" + l);
			out.println(tab + TAB + p.getName() + " "+p.getValue()+";");
			out.println();
		}
		
		
		for(int i = 0;i < childs.size();i++)
		{
			childs.get(i).writeTo(tab + TAB, out);
		}
		
		
		out.println(tab + "}");
		out.println();

	}



	public void add(ConfigSection child) {
		childs.add(child);
	}

	public int countParameter() {
		return parameter.size();
	}

	public int countSections() {
		return childs.size();
	}

	public List<Parameter> getParameter() {
		return parameter;
	}

}
