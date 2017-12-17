# AdminPanel
java library for creating simple admin panel for configuration

The release contains:

- ```adminpanellib.x.y.z.jar``` : Library for embed in your application
- ```adminpaneltest.x.y.z.jar```:  test functionality

## Demo 
```shell
		echo "mysecretmysecret" > temp/secret.txt
		java -jar adminpaneltest.0.0.1.jar
```

## Configure AdminPanel
```java

		//Implement logger interface
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

		//Implement secret provider for encrypting secret information
		AdminPanel.setSecretProvider(new SecretProvider() {

			@Override
			public byte[] get128BitSecret() {
				// 128 bit key, or 16 bytes
			    return "mysecretmysecret".getBytes();
				
			}
		});
		
		//Configure 
		AdminPanel.withDefaults(Locale.GERMANY);
		
		//Configure Forms for datasets (here e.G. email accounts and data files)
		AdminPanel.createPage("account","Kontos").setForm(EmailAccount.class);
		AdminPanel.createPage("files","Dateien").setForm(DataFile.class);
		
		//Configure access rights for Users (means user must register before)
		AdminPanel.selection().forGroup(Group.User).forPage("account").forPage("files").allowAll();

```

## Use as Servlet 
```java
		//Create Jetty Server
		Server server = new Server(new InetSocketAddress("localhost", 3333));

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		ServletHandler handler = context.getServletHandler();
		
		//Register AdminPanel Servlet to path
		handler.addServletWithMapping(new ServletHolder(new AdminPanelServlet()), "/admin/*");
		
		server.setHandler(context);

		// Start things up!
		server.start();


		// Wait until the server is done executing.
		server.join();

```