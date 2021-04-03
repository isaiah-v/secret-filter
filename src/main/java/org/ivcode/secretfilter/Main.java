package org.ivcode.secretfilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	
	public static void main(String... args) throws IOException {
		var application = new SpringApplication(Main.class);
		
		var defaultProperties = new HashMap<String,Object>();
		defaultProperties.put("version", getVersion());
		application.setDefaultProperties(defaultProperties);
		
		application.run(args);
	}
	
	private static String getVersion() throws IOException {
		try (var input = Main.class.getClassLoader().getResourceAsStream("version")) {
			return IOUtils.toString(input, StandardCharsets.UTF_8);
		}
	}
}
