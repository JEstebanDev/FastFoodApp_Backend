/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Orders;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-16
 */
@Repository
public interface IReportRepository extends JpaRepository<Orders, Long> {

//	RANK PRODUCTS BY DIFFERENTS PARAMS 
	
	@Query(value = "SELECT Orders.id_product  AS id_product, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
			+ "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill "
			+ "WHERE Bill.status_bill=0 GROUP BY (Orders.id_product) ORDER BY amount DESC", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankProducts();

	@Query(value = "SELECT Orders.id_product  AS id_product, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
			+ "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill "
			+ "WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Orders.id_product) ORDER BY amount DESC", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankProductsByDate(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query(value = "SELECT Orders.id_product  AS id_product, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
			+ "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill "
			+ "WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate AND Orders.id_product=:idProduct GROUP BY (Orders.id_product) ORDER BY amount DESC LIMIT :limit ", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankProductsByIdProductsAndDateAndLimit(@Param("idProduct") Long idProduct, @Param("limit") Integer limit, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT Orders.id_product  AS id_product, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
			+ "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill "
			+ "WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate AND Orders.id_product=:idProduct GROUP BY (Orders.id_product) ORDER BY amount DESC", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankProductsByIdProductsAndDate(@Param("idProduct") Long idProduct,@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT Orders.id_product  AS id_product, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
			+ "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill "
			+ "WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Orders.id_product) ORDER BY amount DESC LIMIT :limit ", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankProductsByDateAndLimit(@Param("limit") Integer limit,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT Orders.id_product  AS id_product, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
			+ "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill "
			+ "WHERE Bill.status_bill=0 AND Orders.id_product=:idProduct GROUP BY (Orders.id_product) ORDER BY amount DESC LIMIT :limit ", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankProductsByIdProductsAndLimit(@Param("idProduct") Long idProduct,
			@Param("limit") Integer limit);

	@Query(value = "SELECT Orders.id_product  AS id_product, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
			+ "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill "
			+ "WHERE Bill.status_bill=0  AND Orders.id_product=:idProduct GROUP BY (Orders.id_product) ORDER BY amount DESC", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankProductsByIdProducts(@Param("idProduct") Long idProduct);

	@Query(value = "SELECT Orders.id_product  AS id_product, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
			+ "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill "
			+ "WHERE Bill.status_bill=0 GROUP BY (Orders.id_product) ORDER BY amount DESC LIMIT :limit ", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankProductsByLimit(@Param("limit") Integer limit);

	
//	SEARCH FOR BEST CLIENTS
	@Query(value = "SELECT Bill.id_user, SUM(COALESCE(Orders.total,0)) total FROM orders Orders JOIN bill Bill on Bill.id_bill=Orders.id_bill WHERE Bill.status_bill=0 GROUP BY (Bill.id_user)", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankClients();
	
	@Query(value = "SELECT Bill.id_user, SUM(COALESCE(Orders.total,0)) total FROM orders Orders JOIN bill Bill on Bill.id_bill=Orders.id_bill WHERE Bill.status_bill=0 "
			+ "AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Bill.id_user)", nativeQuery = true)
	Collection<Map<String, BigInteger>> getRankClientsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	
//	TOTAL SALES 
	@Query(value = "SELECT Orders.id_bill, SUM(COALESCE(Orders.total,0)) AS total FROM orders Orders "
			+ "JOIN bill Bill on Bill.id_bill=Orders.id_bill WHERE Bill.status_bill=0 AND Bill.date BETWEEN '2022-01-13' AND '2022-03-13' GROUP BY (Orders.id_bill) ORDER BY total DESC", nativeQuery = true)
	Collection<Map<String, BigInteger>> getSalesByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
//	PAYMODE COUNT
	@Query(value = "select id_pay_mode,COUNT(id_pay_mode) quantity from bill WHERE Bill.status_bill=0 AND date BETWEEN '2022-01-13' AND '2022-03-13' GROUP BY (id_pay_mode)", nativeQuery = true)
	Collection<Map<String, BigInteger>> getSalesPayModeByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}