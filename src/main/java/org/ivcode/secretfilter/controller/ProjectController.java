package org.ivcode.secretfilter.controller;

import java.util.List;

import org.ivcode.secretfilter.controller.model.ProjectDTO;
import org.ivcode.secretfilter.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Authorization")
public class ProjectController {
	
	@Autowired
	private ProjectsService projectService;
	
	@Operation(description = "Returns available projects")
	@GetMapping(path = "/projects")
	@PreAuthorize("hasAuthority('SCOPE_properties.read')")
	public List<String> getPaths() {
		return projectService.getPaths();
	}
	
	@Operation(description = "Returns the details for the project defined at the given path")
	@GetMapping(path = "/projects/{project}")
	@PreAuthorize("hasAuthority('SCOPE_properties.read')")
	public ProjectDTO getProject(
			@PathVariable("project") @Parameter(description = "project's path variable") String path) {
		return projectService.readProject(path);
	}
	
	@Operation(description = "Creates a project at the given path")
	@PostMapping(path = "/projects/{project}")
	@PreAuthorize("hasAuthority('SCOPE_properties.write')")
	public void postProject(
			@PathVariable("project") @Parameter(description = "project's path variable") String path,
			@RequestBody ProjectDTO dto) {
		projectService.createProject(path, dto);
	}
	
	@Operation(description = "Update the project at the given path")
	@PutMapping(path = "/projects/{project}")
	@PreAuthorize("hasAuthority('SCOPE_properties.write')")
	public void putProject(
			@PathVariable("project") @Parameter(description = "project's path variable") String path,
			@RequestBody ProjectDTO dto) {
		projectService.updateProject(path, dto);
	}
	
	@Operation(description = "Deletes the project at the given path")
	@DeleteMapping(path = "/projects/{project}")
	@PreAuthorize("hasAuthority('SCOPE_properties.write')")
	public void deleteProject(
			@PathVariable("project") @Parameter(description = "project's path variable") String path) {
		projectService.deleteProject(path);
	}
}
