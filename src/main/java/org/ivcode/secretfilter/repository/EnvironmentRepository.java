package org.ivcode.secretfilter.repository;

import java.util.List;

import javax.annotation.Resource;

import org.ivcode.secretfilter.repository.model.EnvironmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Manages precedences of {@link EnvironmentEntity}s
 * 
 * @author isaiah
 *
 */
@Resource
public interface EnvironmentRepository extends JpaRepository<EnvironmentEntity, Integer> {

	@Query("SELECT e FROM EnvironmentEntity e WHERE UPPER(e.project.path)=UPPER(:project)")
	public List<EnvironmentEntity> getEnvironments(@Param("project") String project);

	@Query("SELECT e FROM EnvironmentEntity e WHERE UPPER(e.project.path)=UPPER(:project) AND UPPER(e.path)=UPPER(:env)")
	public EnvironmentEntity getEnvironment(@Param("project") String project, @Param("env") String env);

	@Modifying
	@Query("DELETE FROM EnvironmentEntity e WHERE e=:entity")
	public void deleteEnvironment(@Param("entity") EnvironmentEntity entity);
}
