package de.frittenburger.core;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class I18n {

	
	private static ResourceBundle messages;
	public static void init(Locale locale)
	{
		messages = ResourceBundle.getBundle("MessageBundle", locale);
	}
	

	private static String createKey(String text)
	{
		Matcher m = Pattern.compile("[_\\s'-]([A-Za-z])").matcher(text);
	    StringBuilder sb = new StringBuilder();
	    int last = 0;
	    while (m.find()) {
	        sb.append(text.substring(last, m.start()));
	        sb.append(m.group(1).toUpperCase());
	        last = m.end();
	    }
	    sb.append(text.substring(last));
	    
	    String key = sb.toString().trim();
	    key = key.replaceAll(" ", "_");
	    if(key.length() > 16)
	    	return key.substring(0, 15);
	    return key;
	}
	
	
	private static String format(String format, Object[] args) {
		
		
		Matcher m = Pattern.compile("\\{([0-9]+)\\}").matcher(format);

	    StringBuilder sb = new StringBuilder();
	    int last = 0;
	    while (m.find()) {
	        sb.append(format.substring(last, m.start()));
	        
	        int i = Integer.parseInt(m.group(1));
	        
	        if(i < 0 || i >= args.length)
	        {
	        	sb.append("_"+i+"_");
	        }
	        else
	        {
		        Object o = args[i];
		        
		        if(o instanceof String)
		        {
		        	sb.append(String.format("%s", args[i]));
		        }
		        else
		        {
		        	throw new RuntimeException("Unknown type "+o);
		        }
	        }
	        last = m.end();
	    }
	    sb.append(format.substring(last));
	   
	    return sb.toString();
	}
	
	public static String translate(String format,Object ... args) {
		String key = createKey(format);
		if(messages.containsKey(key))
		{
			format = messages.getString(key);
		}
		else
		{
			System.err.println("I18N: Key not found "+key);
		}
		return format(format, args);
	}
	



	//mark and Translate
	public static String tr(String format,Object ... args) {
		return translate(format,args);
	}

    //only mark
	public static String marktr(String text) {
		return text;
	}


	

}
