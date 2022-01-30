/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Additional;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@Repository
public interface IAdditionalRepository extends JpaRepository<Additional, Long> {
	Additional findByName(String name);
}
