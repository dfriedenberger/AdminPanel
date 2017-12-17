package de.frittenburger.html;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;


public class HtmlContainer implements HtmlComponent {
	
	private String name;
	private List<HtmlComponent> components;
	private List<SimpleEntry<String,String>> attributes;
	
	
	
	private static HtmlComponent nullSpace = new HtmlComponent() {
		@Override
		public void writeTo(HtmlWriter writer) { }
	};
		
	private static HtmlComponent newLine = new HtmlComponent() {
		@Override
		public void writeTo(HtmlWriter writer) {
			writer.printLine("");			
		}
	};
	
	private	class Comment implements HtmlComponent {

			public String text;
			
			@Override
			public void writeTo(HtmlWriter writer) {
				writer.printLine("<!--"+text+"-->");
			}
	}
	
	private	class Text implements HtmlComponent {

		public String text;
		
		@Override
		public void writeTo(HtmlWriter writer) {
			writer.print(text);
		}
}
		
	public HtmlContainer(String name) {
		this.name = name;
		components = new ArrayList<HtmlComponent>();
		attributes = new ArrayList<SimpleEntry<String,String>>();
	}

	@Override
	public void writeTo(HtmlWriter writer) {
		
		
		switch(components.size())
		{
		case 0:
			writer.writeOpenClose(name,attributes);
			break;
		case 1:
			HtmlComponent c = components.get(0);
			//StringWriter stringWriter = new StringWriter();
			if(c == nullSpace)
			{
				writer.writeOpenClose(name,attributes,"");
				break;
			}
			if(c instanceof Text)
			{
				writer.writeOpenClose(name,attributes,((Text)c).text);
				break;
			}
			/* FALLTROUGH */
			default:
				writer.writeOpen(name,attributes);
				
				for(HtmlComponent comp : components)
					comp.writeTo(writer);
				
				writer.writeClose(name);	
			break;
			
		}
		
	}

	public HtmlContainer set(String key, String value) {
		attributes.add(new SimpleEntry<String,String>(key,value));
		return this;
	}
	
	public HtmlContainer addClass(String classname) {

		for(SimpleEntry<String,String> e : attributes)
			if(e.getKey().equals("class"))
			{
				e.setValue(e.getValue()+" "+classname);
				return this;
			}
			
		return set("class",classname);
	}
	
	public void addComponent(HtmlComponent comp) {
		components.add(comp);	
	}
	public void addComment(String text) {
		Comment comment = new Comment();
		comment.text = text;
		components.add(comment);
	}
	
	public HtmlContainer addDiv(String class1) {
		HtmlContainer div = new HtmlContainer("div").addClass(class1);
		components.add(div);
		return div;
	}

	public HtmlContainer addIcon(String class1, String text) {
		HtmlContainer i = new HtmlContainer("i").addClass(class1);
		i.addText(text);
		components.add(i);
		return i;
		
	}
	public HtmlContainer addInput(String id) {
		HtmlContainer input = new HtmlContainer("input").set("name",id);
		components.add(input);
		return input;
	}
	public HtmlContainer addSelect(String id, String[] values) {

		HtmlContainer select = new HtmlContainer("select").set("name",id);
		for(String val : values)
			select.addOption(val);
		components.add(select);
		return select;
	}

	public HtmlContainer addOption(String val) {
		HtmlContainer option = new HtmlContainer("option");
		option.addText(val);
		components.add(option);
		return option;
	}

	public HtmlContainer addLabel(String id) {
		HtmlContainer label = new HtmlContainer("label").set("for",id);
		components.add(label);
		return label;
	}


	public void newLine() {
		components.add(newLine);		
	}

	public HtmlContainer addCssLink(String href) {
		
		HtmlContainer link = new HtmlContainer("link");
		link.set("type","text/css");
		link.set("href",href);
		components.add(link);
		return link;
	
	}
	
	public HtmlContainer addMeta(String name, String content) {
		HtmlContainer meta = new HtmlContainer("meta");
		meta.set("name",name);
		meta.set("content",content);
		components.add(meta);
		return meta;		
	}

	public HtmlContainer addJavaScript(String src) {
		
		HtmlContainer script = new HtmlContainer("script");
		script.set("type","text/javascript");
		script.set("src",src);
		script.addComponent(nullSpace);		
		components.add(script);
		return script;		
		
	}

	public HtmlContainer addAhref(String href) {
		HtmlContainer a = new HtmlContainer("a");
		a.set("href",href);
		components.add(a);
		return a;
	}

	
	
	public void addText(String text) {
		Text t = new Text();
		t.text = text;
		components.add(t);
	}

	public HtmlContainer addListItem() {
		HtmlContainer li = new HtmlContainer("li");
		components.add(li);
		return li;
	}

	
	

	
	
	
	
	

	

	
	

	
}
