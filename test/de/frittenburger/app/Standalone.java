package de.frittenburger.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import de.frittenburger.core.AdminPanel;
import de.frittenburger.core.Group;
import de.frittenburger.core.LoggerListener;
import de.frittenburger.core.SecretProvider;
import de.frittenburger.form.EmailAccount;
import de.frittenburger.form.DataFile;
import de.frittenburger.web.AdminPanelServlet;

public class Standalone {

	public static void main(String[] args) throws Exception {
		
		//Create Jetty Server
		Server server = new Server(new InetSocketAddress("localhost", 3333));

		
		AdminPanel.setLoggerListener(new LoggerListener() {

			@Override
			public void log(int level, String message,String dump) {
				if(level == 0)
				{
					System.err.println(message);
					if(dump != null)
						System.err.println(dump);
				}
				else
				{
					System.out.println(message);
					if(dump != null)
						System.out.println(dump);
				}
			}
		});
		
		AdminPanel.setSecretProvider(new SecretProvider() {

			@Override
			public byte[] get128BitSecret() {
				// 128 bit key, 
				Path path = Paths.get("temp/secret.txt");
				byte[] data;
				try {
					data = Files.readAllBytes(path);
				} catch (IOException e) {
					throw new RuntimeException("No such file");
				}
				return Arrays.copyOf(data, 16);
			}
		});
		
		AdminPanel.withDefaults(Locale.GERMANY);
		AdminPanel.createPage("account","Kontos").setForm(EmailAccount.class);
		AdminPanel.createPage("files","Dateien").setSingletonForm(DataFile.class);
		AdminPanel.selection().forGroup(Group.User).forPage("account").forPage("files").allowAll();

		
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		ServletHandler handler = context.getServletHandler();
		handler.addServletWithMapping(new ServletHolder(new AdminPanelServlet()), "/admin/*");
		
		server.setHandler(context);

		// Start things up!
		server.start();

		// The use of server.join() the will make the current thread join and
		// wait until the server is done executing.
		// See
		// http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
		server.join();
		
		
		
	}

}
