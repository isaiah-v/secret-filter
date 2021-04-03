package org.ivcode.secretfilter.controller;

import java.util.Map;

import org.ivcode.secretfilter.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * A given project can have multiple environments. Each environment can define
 * its own set of properties. This controller defines the calls to manage the
 * properties for a given project and environment.
 * 
 * @author isaiah
 *
 */
@RestController
@SecurityRequirement(name = "Authorization")
public class PropertiesController {
	
	@Autowired
	private PropertiesService service;
	
	@GetMapping(path = "/projects/{project}/environments/{environment}/properties")
	@PreAuthorize("hasAuthority('SCOPE_properties.read')")
	public Map<String, String> getProperties(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath) {
		
		return service.readProperties(projectPath, envPath);
	}
	
	@PostMapping(path = "/projects/{project}/environments/{environment}/properties")
	@PreAuthorize("hasAuthority('SCOPE_properties.write')")
	public void postProperties(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath,
			@RequestBody Map<String, String> properties) {
		
		service.createProperties(projectPath, envPath, properties);
	}
	
	@PutMapping(path = "/projects/{project}/environments/{environment}/properties")
	@PreAuthorize("hasAuthority('SCOPE_properties.write')")
	public void putProperties(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath,
			@RequestBody Map<String, String> properties) {
		
		service.updateProperties(projectPath, envPath, properties);
	}
	
	@DeleteMapping(path = "/projects/{project}/environments/{environment}/properties")
	@PreAuthorize("hasAuthority('SCOPE_properties.write')")
	public void deleteProperties(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath) {
		
		service.deleteProperties(projectPath, envPath);
	}
}
