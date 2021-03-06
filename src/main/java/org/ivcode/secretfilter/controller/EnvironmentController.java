package org.ivcode.secretfilter.controller;

import java.util.List;

import org.ivcode.secretfilter.controller.model.EnvironmentDTO;
import org.ivcode.secretfilter.service.EnvironmentService;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	@Operation(description = "Return the available environment path variables")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project not found", content = @Content(schema = @Schema(hidden = true)))})
	@GetMapping(path = "/projects/{project}/environments", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<String> getPaths(
			@PathVariable("project") String projectPath) {
		return service.getPaths(projectPath);
	}
	
	@Operation(description = "Return the detail information for the environment defined at the given path")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project or environment not found", content = @Content(schema = @Schema(hidden = true)))})
	@GetMapping(path = "/projects/{project}/environments/{environment}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public EnvironmentDTO getProject(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath) {
		return service.readEnvironment(projectPath, envPath);
	}
	
	@Operation(description = "Insert an environment at the given path")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "403", description = "Forbidden - The user has insufficient rights", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project not found", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "409", description = "Conflict - Resource already exists", content = @Content(schema = @Schema(hidden = true)))})
	@PostMapping(path = "/projects/{project}/environments/{environment}")
	public void postProject(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath,
			@RequestBody EnvironmentDTO envDto) {
		
		service.createEnvironment(projectPath, envPath, envDto);
	}
	
	@Operation(description = "Insert or update an environment at the given path")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "403", description = "Forbidden - The user has insufficient rights", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project not found", content = @Content(schema = @Schema(hidden = true)))})
	@PutMapping(path = "/projects/{project}/environments/{environment}")
	public void putProject(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath,
			@RequestBody EnvironmentDTO envDto) {
		
		service.updateEnvironment(projectPath, envPath, envDto);
	}
	
	@Operation(description = "Delete an environment at the given path")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project or environment not found", content = @Content(schema = @Schema(hidden = true)))})
	@DeleteMapping(path = "/projects/{project}/environments/{environment}")
	public void deleteProject(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath) {
		
		service.deleteEnvironment(projectPath, envPath);
	}
}
