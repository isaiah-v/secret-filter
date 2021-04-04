package org.ivcode.secretfilter.controller;

import java.util.List;

import org.ivcode.secretfilter.controller.model.ProjectDTO;
import org.ivcode.secretfilter.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * A given project can have multiple environments. Each environment can define
 * its own set of properties. This controller defines calls to manage the
 * projects.
 * 
 * @author isaiah
 *
 */
@RestController
@SecurityRequirement(name = "Authorization")
public class ProjectController {

	@Autowired
	private ProjectsService projectService;

	@Operation(description = "Returns available projects path variables")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true)))})
	@GetMapping(path = "/projects", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<String> getPaths() {
		
		return projectService.getPaths();
	}

	@Operation(description = "Returns the details for the project defined at the given path")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project not found", content = @Content(schema = @Schema(hidden = true)))})
	@GetMapping(path = "/projects/{project}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ProjectDTO getProject(
			@PathVariable("project") @Parameter(description = "project's path variable") String path) {
		
		return projectService.readProject(path);
	}

	@Operation(description = "Insert a project at the given path")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "403", description = "Forbidden - The user has insufficient rights", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "409", description = "Conflict - Project already exists", content = @Content(schema = @Schema(hidden = true)))})
	@PostMapping(path = "/projects/{project}")
	public void postProject(@PathVariable("project") @Parameter(description = "project's path variable") String path,
			@RequestBody ProjectDTO dto) {
		projectService.createProject(path, dto);
	}

	@Operation(description = "Insert or update a project at the given path")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "403", description = "Forbidden - The user has insufficient rights", content = @Content(schema = @Schema(hidden = true)))})
	@PutMapping(path = "/projects/{project}")
	public void putProject(@PathVariable("project") @Parameter(description = "project's path variable") String path,
			@RequestBody ProjectDTO dto) {
		projectService.updateProject(path, dto);
	}

	@Operation(description = "Deletes the project at the given path")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project not found", content = @Content(schema = @Schema(hidden = true)))})
	@DeleteMapping(path = "/projects/{project}")
	public void deleteProject(
			@PathVariable("project") @Parameter(description = "project's path variable") String path) {
		projectService.deleteProject(path);
	}
}
