package org.ivcode.secretfilter.controller.model;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import org.ivcode.secretfilter.repository.model.ProjectEntity;

import io.swagger.v3.oas.annotations.media.Schema;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
