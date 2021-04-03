package org.ivcode.secretfilter.repository;

import java.util.List;
import java.util.UUID;

import org.ivcode.secretfilter.repository.model.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Manages the persistence of {@link PropertyEntity}s
 * 
 * @author isaiah
 *
 */
@Repository
public interface PropertiesRepository extends JpaRepository<PropertyEntity, UUID> {

	@Query("SELECT p FROM PropertyEntity p WHERE UPPER(p.environment.path)=UPPER(:environment) AND UPPER(p.environment.project.path)=UPPER(:project)")
	public List<PropertyEntity> getProperties(@Param("project") String project,
			@Param("environment") String environment);

}
