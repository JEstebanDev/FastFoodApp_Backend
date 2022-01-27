/**
 * 
 */
package JEstebanC.FastFoodApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import JEstebanC.FastFoodApp.model.Product;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
@Component
public interface IProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

}
