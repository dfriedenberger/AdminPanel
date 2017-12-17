package de.frittenburger.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.core.AdminPanel;
import de.frittenburger.core.Logger;
import de.frittenburger.core.Page;
import de.frittenburger.html.HtmlTemplate;

public class AdminPanelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = AdminPanel.getLogger(this.getClass().getName());
	
	public AdminPanelServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String sessionid = null;
		
		HttpSession ssn = request.getSession();
		if(ssn != null){
			sessionid = ssn.getId();
		}
		
		
		String pathInfo = request.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/"))
		{
			//redirect to + "/"
			String path = request.getServletPath();
			response.sendRedirect(response.encodeRedirectURL(path+"/"+Page.welcome));
			return;
		}
		
	
	
		
		
		if(pathInfo.startsWith("/js/"))
		{
			stream("application/javascript",pathInfo.substring(1),response);
			return;
		}
		
		if(pathInfo.startsWith("/css/"))
		{
			stream("text/css",pathInfo.substring(1),response);
			return;
		}
		
		
		if(pathInfo.startsWith("/fonts/"))
		{
			String ctype = "application/font-woff";
			if(pathInfo.endsWith("woff2")) ctype = "font/woff2";
			stream(ctype,pathInfo.substring(1),response);
			return;
		}
		
		logger.debug("Request=%s sessionid=%s",pathInfo,sessionid);


		HtmlTemplate template = new HtmlTemplate(request.getServletPath());
		GetHandler handler = new GetHandler();
		
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		try {
			handler.handle(sessionid,pathInfo.substring(1),template);
			template.writeTo(response.getWriter());

		} catch (AdminPanelException e) {
			e.printStackTrace();
			if(e.getLevel() == AdminPanelException.TSystem)
			{
				response.getWriter().println("ServerError See Logfiles");
			}
			else
			{
				response.getWriter().println(e.getMessage());
			}
		}
		
		

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pathInfo = request.getPathInfo();
		
		String sessionid = null;
		HttpSession ssn = request.getSession();
		if(ssn != null){
			sessionid = ssn.getId();
		}

		
		
		logger.debug("Request=%s sessionid=%s",pathInfo,sessionid);
		
	   
	    
	    Enumeration<String> names = request.getParameterNames();
	    
	    while(names.hasMoreElements())
	    {
		   String name = names.nextElement();
		   String[] values = request.getParameterValues(name);
		   
		   for(String value : values)
		   {

			   if(name.toLowerCase().contains("pass"))
				   logger.debug("%s=******",name);
			   else
				   logger.debug("%s=%s",name,value);

		   }
		   
	    }
	    
	
		
	    JSMessage mesg = new JSMessage();
	    PostHandler handler = new PostHandler();
		try {
			
			String url = handler.handle(sessionid,pathInfo.substring(1),request.getParameterMap());
			mesg.setState("Ok");
			mesg.setMessage("Todo bien");
			mesg.setRedirect(url);
			
		} catch (AdminPanelException e) {
			e.printStackTrace();
			if(e.getLevel() == 0)
			{
				mesg.setState("Exception");
				mesg.setMessage(e.getMessage());
			}
			else
			{
				mesg.setState("Error");
				mesg.setMessage(e.getMessage());
			}
		
		}
	    //Debug 
		logger.dump(mesg.ToJson(),"Json Response");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(mesg.ToJson());
		
	}
	
	private void stream(String ctype, String path, HttpServletResponse response) throws IOException {
	
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream is = classLoader.getResourceAsStream(path);
		response.setContentType(ctype);
		response.setStatus(HttpServletResponse.SC_OK);
		
		byte[] data = new byte[0xffffff];
		int contentLength = 0;
		int len;
		while ((len = is.read(data,contentLength,data.length - contentLength)) != -1) {
			contentLength += len;
		}

		//response here is the HttpServletResponse object
		response.setContentLength(contentLength);
		OutputStream output = response.getOutputStream();
		
		output.write(data,0,contentLength);
	}

	
	
	
}
