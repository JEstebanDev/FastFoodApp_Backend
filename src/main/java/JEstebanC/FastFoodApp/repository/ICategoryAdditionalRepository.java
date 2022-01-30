/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.CategoryAdditional;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-01-24
 */
@Repository
public interface ICategoryAdditionalRepository extends JpaRepository<CategoryAdditional, Long>{
	CategoryAdditional findByName(String name);
}
