package org.ivcode.secretfilter.repository.model;

import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECTS")
public class ProjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROJECT_ID")
	private Integer projectId;

	@Column(name = "PATH")
	private String path;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;
	
	public ProjectEntity() {
	}
	
	public ProjectEntity(ProjectEntity entity) {
		notNull(entity);
		
		this.projectId = entity.projectId;
		this.path = entity.path;
		this.name = entity.name;
		this.description = entity.description;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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
	
	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
