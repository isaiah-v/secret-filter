package org.ivcode.secretfilter.service;

import static java.util.stream.Collectors.*;
import static org.ivcode.secretfilter.utils.CollectionSafety.*;

import java.util.List;

import javax.transaction.Transactional;

import org.ivcode.secretfilter.controller.model.ProjectDTO;
import org.ivcode.secretfilter.exception.ConflictException;
import org.ivcode.secretfilter.exception.NotFoundException;
import org.ivcode.secretfilter.repository.ProjectRepository;
import org.ivcode.secretfilter.repository.model.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Performs business logic related to a project
 * 
 * @author isaiah
 *
 */
@Service
public class ProjectsService {

	@Autowired
	private ProjectRepository repository;

	public List<String> getPaths() {
		return safe(repository.findAll()).stream().map(ProjectEntity::getPath).collect(toList());
	}

	@Transactional
	public void createProject(String path, ProjectDTO dto) {
		var existing = repository.find(path);
		
		if (existing != null) {
			// 409 conflict
			throw new ConflictException();
		}

		var entity = createEntity(path, dto);
		repository.save(entity);
	}

	@Transactional
	public ProjectDTO readProject(String path) {
		var entity = repository.find(path);

		if (entity == null) {
			// 404 not found
			throw new NotFoundException();
		}

		return new ProjectDTO(entity);
	}

	@Transactional
	public ProjectEntity readEntity(String path) {
		var entity = repository.find(path);
		return entity;
	}

	@Transactional
	public void updateProject(String path, ProjectDTO dto) {
		var entity = repository.find(path);
		if (entity == null) {
			repository.save(createEntity(path, dto));
		} else {
			updateEntity(entity, path, dto);
		}
	}

	@Transactional
	public void deleteProject(String path) {
		if(repository.deleteProject(path)==0) {
			throw new NotFoundException();
		}
	}

	private ProjectEntity createEntity(String path, ProjectDTO dto) {
		var entity = new ProjectEntity();
		updateEntity(entity, path, dto);

		return entity;
	}

	private void updateEntity(ProjectEntity entity, String path, ProjectDTO dto) {
		entity.setPath(path);
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
	}

}
