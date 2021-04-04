package org.ivcode.secretfilter.controller;

import java.util.Map;

import org.ivcode.secretfilter.service.PropertyService;
import org.ivcode.secretfilter.utils.SecurityUtils;
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
 * properties for a given project and environment.
 * 
 * @author isaiah
 *
 */
@RestController
@SecurityRequirement(name = "Authorization")
public class PropertyController {
	
	@Autowired
	private PropertyService service;
	
	@Operation(description = "Return the available properties for the given project and environment. Note the property values returned will be masked unless the user has admin rights or the environment is set as readable")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project or environment not found", content = @Content(schema = @Schema(hidden = true)))})
	@GetMapping(path = "/projects/{project}/environments/{environment}/properties", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, String> getProperties(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath) {
		
		return service.readProperties(projectPath, envPath, SecurityUtils.isLimitedAccess());
	}
	
	@Operation(description = "Insert the properties for the given project and environment")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "Bad Request - Empty properties"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "403", description = "Forbidden - The user has insufficient rights", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project or environment not found", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "409", description = "Conflict - Properties already exists", content = @Content(schema = @Schema(hidden = true)))})
	@PostMapping(path = "/projects/{project}/environments/{environment}/properties")
	public void postProperties(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath,
			@RequestBody Map<String, String> properties) {
		
		service.createProperties(projectPath, envPath, properties);
	}
	
	@Operation(description = "Insert or updates the properties for the given project and environment")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "Bad Request - Empty properties"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "403", description = "Forbidden - The user has insufficient rights", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project or environment not found", content = @Content(schema = @Schema(hidden = true)))})
	@PutMapping(path = "/projects/{project}/environments/{environment}/properties")
	public void putProperties(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath,
			@RequestBody Map<String, String> properties) {
		
		service.updateProperties(projectPath, envPath, properties);
	}
	
	@Operation(description = "Insert or updates the properties for the given project and environment")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "401", description = "Unauthorized - The user is not authorized", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "403", description = "Forbidden - The user has insufficient rights", content = @Content(schema = @Schema(hidden = true))),
		@ApiResponse(responseCode = "404", description = "Not Found - Project or environment not found", content = @Content(schema = @Schema(hidden = true)))})
	@DeleteMapping(path = "/projects/{project}/environments/{environment}/properties")
	public void deleteProperties(
			@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath) {
		
		service.deleteProperties(projectPath, envPath);
	}
}
