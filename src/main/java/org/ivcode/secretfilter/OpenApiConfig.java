package org.ivcode.secretfilter;

import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Open API
 * @author isaiah
 */

@OpenAPIDefinition(
		info = @Info(
				title = "Secret-Filter",
				description = "This is a dev-ops properties filter. It assigns private values at deployment",
				version = "0.1"))
@SecurityScheme(
		name = "Authorization",
		type = SecuritySchemeType.OAUTH2,
		flows = @OAuthFlows(
				implicit = @OAuthFlow(
						authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
						tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
						scopes = {
								@OAuthScope(name = "properties.read", description = "read access"),
								@OAuthScope(name = "properties.write", description = "write access")}),
				clientCredentials = @OAuthFlow(
						authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
						tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
						scopes = {
								@OAuthScope(name = "properties.read", description = "read access"),
								@OAuthScope(name = "properties.write", description = "write access")}),
				authorizationCode = @OAuthFlow(
						authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
						tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
						scopes = {
								@OAuthScope(name = "properties.read", description = "read access"),
								@OAuthScope(name = "properties.write", description = "write access")}),
				password = @OAuthFlow(
							authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
							tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
								scopes = {
									@OAuthScope(name = "properties.read", description = "read access"),
									@OAuthScope(name = "properties.write", description = "write access")})))
public class OpenApiConfig {
}
