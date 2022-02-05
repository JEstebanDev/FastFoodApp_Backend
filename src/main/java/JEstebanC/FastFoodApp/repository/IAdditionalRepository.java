/**
 * 
 */
package JEstebanC.FastFoodApp.repository;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Additional;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@Repository
public interface IAdditionalRepository extends JpaRepository<Additional, Long> {
	
	Additional findByIdAdditional(Long idAdditional);
	
	@Query(value = "SELECT * FROM additional WHERE name like ?%", nativeQuery = true)
	Collection<Additional> findByName(String name);
}

