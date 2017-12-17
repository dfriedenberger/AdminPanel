package de.frittenburger.core;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

import org.junit.Test;

import de.frittenburger.bo.AdminPanelException;
import de.frittenburger.form.EmailAccount;

public class TestDatabase {

	
	private void init()
	{
		File temp = new File("temp");
		new File(temp,"emailaccount.conf").delete();
		
		AdminPanel.context().setPath("temp");
		I18n.init(Locale.GERMAN);
		AdminPanel.setSecretProvider(new SecretProvider(){

			@Override
			public byte[] get128BitSecret() {
				return "1234567812345678".getBytes();
			}});
	}
	
	@Test
	public void testAddForm() throws AdminPanelException {
		
		init();
	
		Database database = new Database();
		
		EmailAccount account = database.createForm(EmailAccount.class);
		account.username.setValue("testusername");
		account.password.setValue("password");
		account.server.setValue("server");


		database.addForm(UUID.randomUUID().toString(), account);
		
		assertEquals(1,database.countForm(EmailAccount.class));
		
		
	
	}

}
