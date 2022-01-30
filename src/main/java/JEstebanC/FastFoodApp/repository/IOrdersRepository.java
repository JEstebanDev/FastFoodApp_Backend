/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Orders;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-01-27
 */
@Repository
public interface IOrdersRepository extends JpaRepository<Orders, Long>{
	@Query( value = "SELECT ord.* FROM orders ord join bill bi on ord.id_bill=bi.id_bill where bi.id_bill=?" , nativeQuery = true)
	Collection<Orders> findByIdBill(Long idBill);
	
}
