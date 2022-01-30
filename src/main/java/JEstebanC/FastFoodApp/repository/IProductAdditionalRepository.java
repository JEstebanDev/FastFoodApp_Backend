/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Product_Additional;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@Repository
@EnableJpaRepositories
public interface IProductAdditionalRepository extends JpaRepository<Product_Additional, Long> {

	@Query(value = "SELECT pin.* FROM product_ingredient pin JOIN ingredient ing on ing.id_ingredient=pin.id_ingredient where pin.id_product=?",nativeQuery = true)
	Collection<Product_Additional> findByIdProduct(Long idProduct);
}
