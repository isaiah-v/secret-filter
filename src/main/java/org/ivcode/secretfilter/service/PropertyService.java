package org.ivcode.secretfilter.service;

import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.ivcode.secretfilter.exception.BadRequestException;
import org.ivcode.secretfilter.exception.ConflictException;
import org.ivcode.secretfilter.exception.NotFoundException;
import org.ivcode.secretfilter.repository.PropertiesRepository;
import org.ivcode.secretfilter.repository.model.EnvironmentEntity;
import org.ivcode.secretfilter.repository.model.PropertyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Performs any business logic related to the properties
 * 
 * @author isaiah
 *
 */
@Service
public class PropertyService {

	private static final String VALUE_MASK = "******";
	
	@Autowired
	private PropertiesRepository repository;

	@Autowired
	private EnvironmentService environmentService;

	@Transactional
	public void createProperties(String projectPath, String envPath, Map<String, String> properties) {
		if (properties == null || properties.isEmpty()) {
			// 400 - properties must be defined
			throw new BadRequestException("property values not defined");
		}

		var existing = repository.getProperties(projectPath, envPath);
		if (existing != null && !existing.isEmpty()) {
			// 409 - Conflict
			throw new ConflictException("properties already defined");
		}

		var env = environmentService.readEntity(projectPath, envPath);
		if (env == null) {
			// 404
			throw new NotFoundException("project or environment does not exist");
		}

		var entities = toEntities(env, properties);
		repository.saveAll(entities);
	}

	@Transactional
	public Map<String, String> readProperties(String projectPath, String envPath, boolean limited) {
		var env = environmentService.readEntity(projectPath, envPath);
		if(env==null) {
			// 404 - env or project not found
			throw new NotFoundException("project or environment does not exist");
		}
		
		var entities = repository.getProperties(projectPath, envPath);
		return toProperties(entities, limited);
	}

	@Transactional
	public void updateProperties(String projectPath, String envPath, Map<String, String> properties) {
		if (properties == null || properties.isEmpty()) {
			// 400 - properties must be defined
			throw new BadRequestException("property values not defined");
		}

		var entities = repository.getProperties(projectPath, envPath);

		var lookup = properties.entrySet()
				.stream()
				.map(e -> new MutablePair<>(e.getKey(), e.getValue()))
				.collect(toMap(p -> trim(lowerCase(p.left)), p -> p));

		EnvironmentEntity env = null;

		for (var entity : entities) {
			if (env == null) {
				env = entity.getEnvironment();
			}

			var key = trim(lowerCase(entity.getName()));

			var update = lookup.remove(key);
			if (update != null) {
				// update value
				entity.setName(update.getLeft());
				entity.setValue(update.getRight());
			} else {
				// delete value
				repository.delete(entity);
			}
		}

		var adds = lookup.values().stream().collect(toMap(Pair::getLeft, Pair::getRight));

		if (!adds.isEmpty()) {
			env = env != null ? env : environmentService.readEntity(projectPath, envPath);
			repository.saveAll(toEntities(env, adds));
		}
	}

	@Transactional
	public void deleteProperties(String projectPath, String envPath) {
		var entities = repository.getProperties(projectPath, envPath);
		if(entities==null || entities.isEmpty()) {
			// 404
			throw new NotFoundException("project or environment does not exist");
		}
		
		repository.deleteAll(entities);
	}

	private List<PropertyEntity> toEntities(EnvironmentEntity env, Map<String, String> properties) {
		var entities = new ArrayList<PropertyEntity>();

		for (var e : properties.entrySet()) {
			var name = trimToNull(e.getKey());
			var value = trimToEmpty(e.getValue());

			// make sure both the key and value are defined
			if (name == null) {
				throw new BadRequestException("property name not defined: \"" + e.getKey() + "\" = \"" + e.getValue() + "\"");
			}

			var entity = new PropertyEntity();
			entity.setEnvironment(env);
			entity.setName(name);
			entity.setValue(value);

			entities.add(entity);
		}

		return entities;
	}

	private Map<String, String> toProperties(List<PropertyEntity> entities, boolean limited) {
		var properties = new LinkedHashMap<String, String>();
		
		for(var entity : entities) {
			var isMask = limited && !Boolean.TRUE.equals(entity.getEnvironment().getReadable());
			properties.put(entity.getName(), isMask ? VALUE_MASK : entity.getValue());
		}
		
		return properties;
	}
}
