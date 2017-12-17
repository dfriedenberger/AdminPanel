package de.frittenburger.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import de.frittenburger.bo.ButtonBar;
import de.frittenburger.bo.Input;
import de.frittenburger.bo.SelectInput;
import de.frittenburger.core.I18n;
import de.frittenburger.core.Page;
import de.frittenburger.core.Wrapper;
import de.frittenburger.form.Form;

public class HtmlTemplate {


	private List<String> buffer = new ArrayList<String>();	
	private List<HtmlComponent> menu = new ArrayList<HtmlComponent>();
	private List<HtmlComponent> menuMobile = new ArrayList<HtmlComponent>();
	private List<HtmlComponent> content = new ArrayList<HtmlComponent>();
	private String rootPath;

	public HtmlTemplate(String rootPath)
	{
		this.rootPath = rootPath;
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream is = classLoader.getResourceAsStream("index.html");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		try {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				buffer.add(line);
			}

			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addMenu(List<Page> pages) {
		
		
		for(Page page : pages)
		{
	        //<li><a href="#">Navbar Link mobile</a></li>
			HtmlContainer li = new HtmlContainer("li");
			li.addAhref(page.getKey()).addText(page.getName());
			menu.add(li);
			menuMobile.add(li);
		}
		
	}
	
	public void addContentForm(Form f, String url) {

		HtmlContainer form = new HtmlContainer("form").set("action",url).addClass("col").addClass("s12");
		
		Wrapper wrapper = new Wrapper(f);
	
		List<Entry<String,Input>> inputs = wrapper.getInputFields();
		
		for(Entry<String,Input> e : inputs)
		{
				HtmlContainer div = form.addDiv("row");
				HtmlContainer divinput = div.addDiv("col").addClass("input-field").addClass("s12");
				String id = e.getKey();
				Input input = e.getValue();
				
				if(input instanceof SelectInput)
				{
					SelectInput sInput = (SelectInput)input;
					divinput.addSelect(id,sInput.getValues()).addClass("validate");
				}
				else
				{
					divinput.addInput(id).set("type",input.getType()).addClass("validate");
				}
				divinput.addLabel(id).addText(input.getDescription());
		}
	
		ButtonBar bar = f.getButtonbBar();
		form.addComponent(createButtonBar(bar));
		
		content.add(form);
		
	}

	public <T extends Form> void addContentCollection(List<Entry<String, T>> data, String url, boolean addButton) {

		
		HtmlContainer list = new HtmlContainer("ul").addClass("collection");
		
		for(Entry<String, T> e : data)
		{
			Form f = e.getValue();
			
			HtmlContainer item = list.addListItem().addClass("collection-item");
			
			Wrapper wrapper = new Wrapper(f);
			List<Entry<String,Input>> inputs = wrapper.getInputFields();
			
			for(Entry<String,Input> i : inputs)
			{
				if(i.getValue().mustProtect())
					item.addText("*** ");
				else
					item.addText(i.getValue().getValue()+" ");
			}
			
			item.addAhref("#!").addClass("secondary-content").addIcon("material-icons","edit");
			item.addAhref("#!").addClass("secondary-content").addIcon("material-icons","delete");

		}
		
		content.add(list);
		if(addButton)
			content.add(createButtonBar(new ButtonBar(ButtonBar.Add)));
	}
	
	public void addNotice(String text) {

		HtmlContainer div = new HtmlContainer("div").addClass("row");
		content.add(div);
		
		div.addDiv("col").addClass("s12").addClass("m12").addDiv("card-panel").addClass("teal white-text").addText(text);
		
      
	}

	public void addButton(String text, String url) {

		HtmlContainer div = new HtmlContainer("div").addClass("row"); //ButtonBar
		HtmlContainer divbtn = div.addDiv("col").addClass("s12");
		
		divbtn.addAhref(url).addClass("btn btn-large").addClass("waves-effect waves-light").addText(text);
		content.add(div);
		
	}
	
	public void addFooter() {
	}

	public void writeTo(PrintWriter writer) {

		for (String line : buffer) {

			int i = 0;
			while (true) {
				int s = line.indexOf("{", i);
				if (s < 0)
					break;

				int e = line.indexOf("}", i);
				if (e < s)
					break;

				String key = line.substring(s + 1, e).trim();
				String pre = line.substring(i, s);

				HtmlWriter htmlWriter = new HtmlWriter(writer);

				if (i == 0 && pre.isEmpty()) {
					htmlWriter.setTabOffset(pre);
				} else {
					writer.print(pre);
				}
				writeTo(key, htmlWriter);
				i = e + 1;

			}

			writer.println(line.substring(i));

		}
	}

	public HtmlContainer createButtonBar(ButtonBar bar) {
		
		HtmlContainer div = new HtmlContainer("row"); //ButtonBar
		HtmlContainer divbtn = div.addDiv("col").addClass("s12");

		for(String btn : new String[]{ButtonBar.Cancel, ButtonBar.Save , ButtonBar.Add , ButtonBar.Login })
		{
			
			//if(btn.equals(ButtonBar.Cancel)) divbtn.addClass("pull-s10");
			
			if(!bar.hasButton(btn)) continue;
			HtmlContainer button = new HtmlContainer("button").
					addClass("btn").addClass("waves-effect");
			
			if(btn.equals(ButtonBar.Save)) button.addClass("right").addClass("waves-light").addClass("post");
			if(btn.equals(ButtonBar.Login)) button.addClass("right").addClass("waves-light").addClass("post");
			if(btn.equals(ButtonBar.Add)) button.addClass("right").addClass("waves-light").addClass("create");

			if(btn.equals(ButtonBar.Cancel)) button.addClass("white").addClass("black-text").addClass("cancel");
			
			button.addText(I18n.translate(btn));
			divbtn.addComponent(button);

		}	
		return div;
		
	}
	
	
	private void writeTo(String key, HtmlWriter writer) {
		
		
		if(key.equals("title"))
		{
			writer.print("Admin Panel");
			return;
		}
		if(key.equals("root"))
		{
			writer.print(rootPath);
			return;
		}
		if(key.equals("menu"))
		{
			for(HtmlComponent m : menu)
				m.writeTo(writer);
			return;
		}
		if(key.equals("menuMobile"))
		{
			for(HtmlComponent m : menuMobile)
				m.writeTo(writer);
			return;
		}
		
		if(key.equals("content"))
		{
			for(HtmlComponent c : content)
				c.writeTo(writer);
			return;
		}
		
		throw new NoSuchElementException(key);
		
	}

	



	

	
	



	

}
