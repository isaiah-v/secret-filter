package org.ivcode.secretfilter.service;

import static java.util.stream.Collectors.*;
import static org.ivcode.secretfilter.utils.CollectionSafty.*;

import java.util.List;

import javax.transaction.Transactional;

import org.ivcode.secretfilter.controller.model.ProjectDTO;
import org.ivcode.secretfilter.repository.ProjectRepository;
import org.ivcode.secretfilter.repository.model.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			// TODO error
			throw new RuntimeException();
		}

		var entity = createEntity(path, dto);
		repository.save(entity);
	}

	@Transactional
	public ProjectDTO readProject(String path) {
		var entity = repository.find(path);

		if (entity == null) {
			// TODO 404
		}

		return new ProjectDTO(entity);
	}
	
	@Transactional
	public ProjectEntity readEntity(String path) {
		return repository.find(path);
	}

	@Transactional
	public void updateProject(String path, ProjectDTO dto) {
		var entity = repository.find(path);
		if (entity == null) {
			// TODO error
			throw new RuntimeException();
		}

		updateEntity(entity, path, dto);
	}

	@Transactional
	public void deleteProject(String path) {
		repository.deleteProject(path);
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
