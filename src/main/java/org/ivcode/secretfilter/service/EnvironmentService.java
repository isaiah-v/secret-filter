package org.ivcode.secretfilter.service;

import static org.ivcode.secretfilter.utils.CollectionSafety.*;
import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.List;

import javax.transaction.Transactional;

import static java.util.stream.Collectors.*;

import org.ivcode.secretfilter.controller.model.EnvironmentDTO;
import org.ivcode.secretfilter.repository.EnvironmentRepository;
import org.ivcode.secretfilter.repository.model.EnvironmentEntity;
import org.ivcode.secretfilter.repository.model.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Performs any business logic associated with an environment
 * 
 * @author isaiah
 *
 */
@Service
public class EnvironmentService {

	@Autowired
	private EnvironmentRepository envRepository;

	@Autowired
	private ProjectsService projectService;

	@Transactional
	public List<String> getPaths(String projectPath) {
		projectPath = notBlank(trim(projectPath));
		
		return safe(envRepository.getEnvironments(projectPath))
				.stream()
				.map(EnvironmentEntity::getPath)
				.collect(toList());
	}

	@Transactional
	public void createEnvironment(String projectPath, String envPath, EnvironmentDTO envDto) {
		projectPath = notBlank(trim(projectPath));
		envPath = notBlank(trim(envPath));
		notNull(envDto);
		
		var existing = envRepository.getEnvironment(projectPath, envPath);
		if (existing != null) {
			// TODO
			throw new IllegalArgumentException();
		}

		var project = projectService.readEntity(projectPath);
		if (project == null) {
			// TODO
			throw new IllegalArgumentException();
		}

		var entity = createEntity(envPath, envDto, project);
		envRepository.save(entity);
	}

	@Transactional
	public EnvironmentDTO readEnvironment(String projectPath, String envPath) {
		projectPath = notBlank(trim(projectPath));
		envPath = notBlank(trim(envPath));
		
		var entity = envRepository.getEnvironment(projectPath, envPath);

		if (entity == null) {
			// TODO 404
			throw new IllegalStateException();
		}

		return new EnvironmentDTO(entity);
	}

	@Transactional
	public EnvironmentEntity readEntity(String projectPath, String envPath) {
		projectPath = notBlank(trim(projectPath));
		envPath = notBlank(trim(envPath));
		
		return envRepository.getEnvironment(projectPath, envPath);
	}

	@Transactional
	public void updateEnvironment(String projectPath, String envPath, EnvironmentDTO envDto) {
		projectPath = notBlank(trim(projectPath));
		envPath = notBlank(trim(envPath));
		notNull(envDto);
		
		var existing = envRepository.getEnvironment(projectPath, envPath);
		if (existing == null) {
			// TODO
			throw new IllegalArgumentException();
		}

		updateEntity(existing, envPath, envDto);
	}

	@Transactional
	public void deleteEnvironment(String projectPath, String envPath) {
		projectPath = notBlank(trim(projectPath));
		envPath = notBlank(trim(envPath));
		
		var entity = envRepository.getEnvironment(projectPath, envPath);
		if(entity==null) {
			// TODO 
			throw new IllegalArgumentException();
		}
		
		envRepository.deleteEnvironment(entity);
	}

	private EnvironmentEntity createEntity(String path, EnvironmentDTO envDto, ProjectEntity project) {
		var entity = new EnvironmentEntity();
		entity.setProject(project);
		updateEntity(entity, path, envDto);

		return entity;
	}

	private EnvironmentEntity updateEntity(EnvironmentEntity entity, String path, EnvironmentDTO envDto) {
		entity.setPath(path);
		entity.setName(envDto.getName());
		entity.setDescription(envDto.getDescription());
		entity.setReadable(envDto.isReadable());

		return entity;
	}
}
