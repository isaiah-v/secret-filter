package org.ivcode.secretfilter.controller.model;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import org.ivcode.secretfilter.repository.model.ProjectEntity;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a project. This class contains information about a projects.
 * 
 * @author isaiah
 *
 */
@Schema(name = "Project")
public class ProjectDTO {

	private String name;
	private String description;

	public ProjectDTO() {
	}

	public ProjectDTO(ProjectEntity entity) {
		this.name = entity.getName();
		this.description = entity.getDescription();
	}

	@Schema(description = "The project's name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Schema(description = "A description of the project")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}
}
