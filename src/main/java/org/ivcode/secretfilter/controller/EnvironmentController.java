package org.ivcode.secretfilter.controller;

import java.util.List;

import org.ivcode.secretfilter.controller.model.EnvironmentDTO;
import org.ivcode.secretfilter.service.EnvironmentService;
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
 * environments within a project.
 * 
 * @author isaiah
 *
 */
@RestController
@SecurityRequirement(name = "Authorization")
public class EnvironmentController {
	
	@Autowired
	private EnvironmentService service;
	
	/**
	 * Gets the available environment paths
	 * @param projectPath
	 * 		The project
	 * @return
	 */
	@GetMapping(path = "/projects/{project}/environments")
	@PreAuthorize("hasAuthority('SCOPE_properties.read')")
	public List<String> getPaths(
			@PathVariable("project") String projectPath) {
		return service.getPaths(projectPath);
	}
	
	@GetMapping(path = "/projects/{project}/environments/{environment}")
	@PreAuthorize("hasAuthority('SCOPE_properties.read')")
	public EnvironmentDTO getProject(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath) {
		return service.readEnvironment(projectPath, envPath);
	}
	
	@PostMapping(path = "/projects/{project}/environments/{environment}")
	@PreAuthorize("hasAuthority('SCOPE_properties.write')")
	public void postProject(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath,
			@RequestBody EnvironmentDTO envDto) {
		
		service.createEnvironment(projectPath, envPath, envDto);
	}
	
	@PutMapping(path = "/projects/{project}/environments/{environment}")
	@PreAuthorize("hasAuthority('SCOPE_properties.write')")
	public void putProject(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath,
			@RequestBody EnvironmentDTO envDto) {
		
		service.updateEnvironment(projectPath, envPath, envDto);
	}
	
	@DeleteMapping(path = "/projects/{project}/environments/{environment}")
	@PreAuthorize("hasAuthority('SCOPE_properties.write')")
	public void deleteProject(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath) {
		
		service.deleteEnvironment(projectPath, envPath);
	}
}
