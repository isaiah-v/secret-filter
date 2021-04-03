package org.ivcode.secretfilter.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;

import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.io.StringSubstitutorReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
	
	@Autowired
	private PropertiesService propertiesService;
	
	public Reader createReader(URL url) throws IOException {
		return new InputStreamReader(url.openStream());
	}
	
	public Reader createReader(String value) {
		return new StringReader(value);
	}
	
	public void filter(String projectPath, String envPath, Reader reader, Writer writer) throws IOException {
		var properties = propertiesService.readProperties(projectPath, envPath);
		
		try(var substitutorReader = new StringSubstitutorReader(reader, new StringSubstitutor(properties))) {
			var buffer = new char[1024];
			var size = -1;
			while((size=substitutorReader.read(buffer, 0, buffer.length))!=-1) {
				writer.write(buffer, 0, size);
			}
			
			writer.flush();
		}
	}
}
