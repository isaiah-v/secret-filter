package org.ivcode.secretfilter.repository.model;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROPERTIES")
public class PropertyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROPERTY_ID")
	private Integer propertyId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "VALUE")
	private String value;

	@ManyToOne
	@JoinColumn(name = "ENVIRONMENT_ID")
	private EnvironmentEntity environment;

	public Integer getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EnvironmentEntity getEnvironment() {
		return environment;
	}

	public void setEnvironment(EnvironmentEntity environment) {
		this.environment = environment;
	}
	
	@Override
	public String toString() {
		return reflectionToString(this);
	}
}
