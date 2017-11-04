package de.frittenburger.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import de.frittenburger.bo.ConfigSection;
import de.frittenburger.bo.Parameter;

public class ConfigFile {

	private String filename;
	private List<ConfigSection> sections = null;

	public ConfigFile(String filename) {
		this.filename = filename;
		init();
	}

	public boolean exists() {
		return new File(filename).exists();
	}

	private void init() {
		sections = new ArrayList<ConfigSection>();
	}

	public List<ConfigSection> getSections() {
		return sections;
	}

	public void load() throws IOException {

		init();
		// Open the file
		FileInputStream fstream = new FileInputStream(filename);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		try {
			String strLine;

			Stack<ConfigSection> stack = new Stack<ConfigSection>();
			ConfigSection current = null;
			List<String> comments = new ArrayList<String>();

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				String line = strLine.trim();
				if (line.equals(""))
					continue;
				if (line.equals("}")) {
					// level down
					stack.pop();
					if (stack.size() == 0)
						current = null;
					else
						current = stack.lastElement();
					continue;
				}
				if (line.endsWith("{")) {
					String pre = line.substring(0, line.length() - 1).trim();
					String p[] = pre.split(" ");
					if(p.length != 2) throw new IOException(line);
					// new ConfigSection starts
					current = new ConfigSection(p[0],p[1]);
					// add to parent
					if (stack.size() == 0)
						sections.add(current);
					else
						stack.lastElement().add(current);

					stack.push(current);
					continue;
				}

				if (line.startsWith("#")) {
					comments.add(line.substring(1));
					continue;
				}

				if (line.endsWith(";")) {
					// parameter
					int i = line.indexOf(" ");
					String key = line.substring(0, i);
					String val = line.substring(i + 1, line.length() - 1).trim();
					Parameter p = new Parameter(key);
					for (String c : comments)
						p.addComment(c);
					comments.clear();
					p.setValue(val);
					current.add(p);
					continue;
				}

				throw new IOException(strLine);
			}
		} finally {
			// Close the input stream
			br.close();
		}
	}

	public void add(ConfigSection configSection) {
		sections.add(configSection);
	}

	public void commit() throws IOException {

		PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename)));
		for (int i = 0; i < sections.size(); i++) {
			sections.get(i).writeTo("", out);
		}

		out.close();

	}

	public int count() {
		return sections.size();
	}

}
