package de.frittenburger.html;

import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class HtmlWriter {

	private PrintWriter writer;
	private int deep = 0;
	private String tabOffset = "";

	public HtmlWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public void writeOpen(String tag) {
		writeOpen(tag,new ArrayList<SimpleEntry<String, String>>());
	}
	
	public void writeOpen(String tag, List<SimpleEntry<String, String>> attributes) {
		this.writer.print(tab(deep) + "<"+tag);
		
		for(SimpleEntry<String, String> e : attributes)
			this.writer.print(" "+e.getKey()+"=\""+e.getValue()+"\"");
		
		this.writer.println(">");
		deep++;		
	}

	
	public void writeClose(String tag) {
		deep--;
		this.writer.println(tab(deep) + "</"+tag+">");
	}
	
	public void writeOpenClose(String tag, List<SimpleEntry<String, String>> attributes) {
		
		this.writer.print(tab(deep) + "<"+tag);
		
		for(SimpleEntry<String, String> e : attributes)
			this.writer.print(" "+e.getKey()+"=\""+e.getValue()+"\"");
		
		this.writer.println(" />");
	}
	
	public void writeOpenClose(String tag, List<SimpleEntry<String, String>> attributes, String text) {
		
		this.writer.print(tab(deep) + "<"+tag);
		this.printAttributes(attributes);
		this.writer.println(">"+text+"</"+tag+">");
	
	}

	private void printAttributes(List<SimpleEntry<String, String>> attributes) {
		for(SimpleEntry<String, String> e : attributes)
			this.writer.print(" "+e.getKey()+"=\""+e.getValue()+"\"");		
	}

	private String tab(int deep) {
	
		String t = "";
		for(int i = 0;i < deep;i++)
			t += "    ";
		return tabOffset + t;
	}

	public void printLine(String text) {
		this.writer.println(tab(deep) + text);		
	}

	public void print(String text) {
		this.writer.print(text);		
	}

	public void setTabOffset(String tabOffset) {
		this.tabOffset = tabOffset;		
	}

	

	

}
