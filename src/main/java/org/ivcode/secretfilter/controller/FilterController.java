package org.ivcode.secretfilter.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;

import org.ivcode.secretfilter.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Authorization")
public class FilterController {
	
	@Autowired
	private FilterService filterService;
	
	@GetMapping(path = "/projects/{project}/environments/{environment}/filter", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@PreAuthorize("hasAuthority('SCOPE_properties.read')")
	public ResponseEntity<StreamingResponseBody> getProperties(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath,
			@RequestParam("url") URL url) throws IOException {
		
		Reader reader = filterService.createReader(url);
		
		StreamingResponseBody responseBody = out -> {
		     try {
		    	 filterService.filter(projectPath, envPath, reader, new OutputStreamWriter(out));
		    	 out.flush();
		     } finally {
		    	 reader.close();
		     }
		};
		
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + url.getFile())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(responseBody);
	}
	
}
