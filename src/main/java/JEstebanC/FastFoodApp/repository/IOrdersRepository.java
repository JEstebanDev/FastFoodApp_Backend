/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Orders;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-27
 */
@Repository
public interface IOrdersRepository extends JpaRepository<Orders, Long> {


	@Query(value = "SELECT COUNT(*) FROM orders WHERE id_product=?",nativeQuery = true)
	Long countIdProduct(Long idProduct);

	@Query(value = "SELECT COUNT(*) FROM orders_additional WHERE id_additional=?",nativeQuery = true)
	Long countIdAdditional(Long idAdditional);

	@Query(value = "SELECT ord.* FROM orders ord join bill bi on ord.id_bill=bi.id_bill where bi.id_bill=?", nativeQuery = true)
	Collection<Orders> findByIdBill(Long idBill);

	@Modifying
	@Query(value = "UPDATE bill SET total_price=(SELECT SUM(COALESCE(total,0)) FROM orders WHERE id_bill=:idBill) WHERE id_bill=:idBill", nativeQuery = true)
	void setTotalPriceBill(@Param("idBill") Long idBill);

}
