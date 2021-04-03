package org.ivcode.secretfilter.repository.model;

import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.builder.ToStringBuilder.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * An entity that represents an environment
 * 
 * @author isaiah
 *
 */
@Entity
@Table(name = "ENVIRONMENTS")
public class EnvironmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ENVIRONMENT_ID")
	private Integer environmentId;

	@Column(name = "PATH")
	private String path;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "READABLE")
	private Boolean readable;

	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	private ProjectEntity project;

	public EnvironmentEntity() {
	}

	public EnvironmentEntity(EnvironmentEntity entity) {
		notNull(entity);

		this.environmentId = entity.environmentId;
		this.path = entity.path;
		this.name = entity.name;
		this.description = entity.description;
		this.readable = entity.readable;
		this.project = entity.project == null ? null : new ProjectEntity(entity.project);
	}

	public Integer getEnvironmentId() {
		return environmentId;
	}

	public void setEnvironmentId(Integer environmentId) {
		this.environmentId = environmentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public Boolean getReadable() {
		return readable;
	}

	public void setReadable(Boolean readable) {
		this.readable = readable;
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}
}
