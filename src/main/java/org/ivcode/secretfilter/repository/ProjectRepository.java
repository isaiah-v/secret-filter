package org.ivcode.secretfilter.repository;

import java.util.UUID;

import javax.annotation.Resource;

import org.ivcode.secretfilter.repository.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Manages precedences of {@link ProjectEntity}s
 * @author isaiah
 *
 */
@Resource
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
	@Query("SELECT p FROM ProjectEntity p WHERE UPPER(p.path)=UPPER(:path)")
	public ProjectEntity find(@Param("path") String path);
	
	@Modifying
	@Query("DELETE FROM ProjectEntity p WHERE UPPER(p.path)=UPPER(:path)")
	public void deleteProject(@Param("path") String path);
}
