/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.CategoryAdditional;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-01-24
 */
@Repository
public interface ICategoryAdditionalRepository extends JpaRepository<CategoryAdditional, Long>{
	@Query(value = "SELECT * FROM category_additional WHERE name like ?%",nativeQuery = true)
	Collection<CategoryAdditional> findByName(String name);
}
