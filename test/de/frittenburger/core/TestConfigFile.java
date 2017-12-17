package de.frittenburger.core;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.frittenburger.bo.ConfigSection;
import de.frittenburger.bo.Parameter;
import de.frittenburger.core.ConfigFile;

public class TestConfigFile {

	@Test
	public void test() throws IOException {
		
		
		ConfigFile file = new ConfigFile("temp/test.conf");
		
		Parameter p = new Parameter("par");
	    p.addComment("comment1");
		p.addComment("Comment2");
		p.setValue("value");
		
		ConfigSection level1_0 = new ConfigSection("level1","id1");
		level1_0.add(p);
		level1_0.add(p);

		ConfigSection level2_0 = new ConfigSection("level2","subid1");
		level2_0.add(p);
		level1_0.add(level2_0);
		
		ConfigSection level1_1 = new ConfigSection("level1","id2");
		level1_1.add(p);
		level1_1.add(p);
		
		file.add(level1_0);
		file.add(level1_1);

		file.commit();
		
		
		file.load();
		assertEquals(2, file.count());
		assertEquals("id1", file.getSections().get(0).getId());
		assertEquals(2, file.getSections().get(0).countParameter());
		assertEquals(1, file.getSections().get(0).countSections());
		assertEquals("id2", file.getSections().get(1).getId());
		assertEquals(2, file.getSections().get(1).countParameter());
		assertEquals(0, file.getSections().get(1).countSections());
		
	}

}
