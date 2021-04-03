package org.ivcode.secretfilter.controller.model;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import org.ivcode.secretfilter.repository.model.EnvironmentEntity;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents an environment. This class contains information about an
 * environment.
 * 
 * @author isaiah
 *
 */
@Schema(name = "Environment")
public class EnvironmentDTO {

	private String name;
	private String description;
	private boolean readable;

	public EnvironmentDTO() {
	}

	public EnvironmentDTO(EnvironmentEntity entity) {
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.readable = Boolean.TRUE.equals(entity.getReadable());
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

	public boolean isReadable() {
		return readable;
	}

	public void setReadable(boolean readable) {
		this.readable = readable;
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}
}
