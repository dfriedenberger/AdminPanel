package de.frittenburger.core;

import java.util.Locale;

import de.frittenburger.form.UserAccount;
import de.frittenburger.pages.WelcomePageCallback;

public class AdminPanel {

	
	
	

	private static ConfigurationContext configurationContext = new ConfigurationContext();
	public static SecretProvider secretProvider = null;
	
	
	public static void setSecretProvider(SecretProvider sp)
	{
		secretProvider = sp;
	}
	public static void withDefaults(Locale locale) {
		
		
		I18n.init(locale);
		configurationContext.setPath("config");
		//Configure available groups - defaults
		createGroup(Group.Admin).addUser("admin");
		createGroup(Group.Guest).addUser("guest");
		createGroup(Group.User);
		
		createPage(Page.welcome,I18n.tr("Welcome")).setCallback(new WelcomePageCallback());

		createPage(Page.users,I18n.tr("Users")).setForm(UserAccount.class);

		selection().forGroup(Group.Guest).forPage("welcome").showNotInMenu().allowRead();
		selection().forGroup(Group.Guest).forPage("users").showNotInMenu().allowCreate();
		
	}
	
	public static Page createPage(String key, String name) {
		return configurationContext.addPage(key,name);	
	}

	public static Group createGroup(String name) {
		return configurationContext.addGroup(name);
	}

	public static Selection selection() {
		return configurationContext.newSelection();
	}

	
	public static ConfigurationContext context() {
		return configurationContext;
	}
	
		

}
