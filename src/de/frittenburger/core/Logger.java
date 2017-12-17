package de.frittenburger.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

  
	private LoggerListener loggerListener;
	private String name;

	public Logger(String name, LoggerListener loggerListener) {
		this.name = name;
		this.loggerListener = loggerListener;
	}

	public void debug(String format, Object ... args ) {
		delegate(loggerListener,name,3,"[DEBUG]",new Date(),null,format,args);
	}

	private static synchronized void delegate(LoggerListener loggerListener, String name, int level, String tag, Date date, String dump,String format,
			Object[] args) {

		DateFormat dateFormat = new SimpleDateFormat();
		
		StringBuilder message = new StringBuilder(name);
		message.append(dateFormat.format(date));
		message.append(tag);
		message.append(String.format(format, args));
		
		if(loggerListener == null)
		{
			System.out.println(message);
			if(dump != null)
				System.out.println(message);
		}
		else
			loggerListener.log(level,message.toString(),dump);

		
		
	}

	public void dump(String message, String format, Object ... args) {
		delegate(loggerListener,name,3,"[DEBUG]",new Date(),message,format,args);
		
	}

}
