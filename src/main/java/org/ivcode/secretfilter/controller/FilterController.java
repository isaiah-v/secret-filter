package org.ivcode.secretfilter.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;

import org.ivcode.secretfilter.service.FilterService;
import org.ivcode.secretfilter.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * This controller pulls everything together. It's used to pull a resource over
 * the Internet and apply the filter, replacing any placeholders with property
 * values.
 * 
 * @author isaiah
 *
 */
@RestController
@SecurityRequirement(name = "Authorization")
public class FilterController {

	@Autowired
	private FilterService filterService;

	@Operation(description = "Replaces a resource's placeholds with the property values defines in the defined project and environment")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(hidden = true)))})
	@GetMapping(path = "/projects/{project}/environments/{environment}/filter", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<StreamingResponseBody> getProperties(@PathVariable("project") String projectPath,
			@PathVariable("environment") String envPath, @RequestParam("url") URL url) throws IOException {

		Reader reader = filterService.createReader(url);

		StreamingResponseBody responseBody = out -> {
			try {
				filterService.filter(projectPath, envPath, reader, new OutputStreamWriter(out), SecurityUtils.isLimitedAccess());
				out.flush();
			} finally {
				reader.close();
			}
		};

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + url.getFile())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(responseBody);
	}

}
