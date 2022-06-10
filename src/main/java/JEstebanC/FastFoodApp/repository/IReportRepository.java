/**
 *
 */
package JEstebanC.FastFoodApp.repository;

import JEstebanC.FastFoodApp.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-16
 */
@Repository
public interface IReportRepository extends JpaRepository<Orders, Long> {

//	RANK PRODUCTS BY DIFFERENTS PARAMS 

    @Query(value = "SELECT Orders.id_product  AS id_product, Product.name AS name, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
            + "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill JOIN product Product ON Product.id_product=Orders.id_product "
            + "WHERE Bill.status_bill=0 GROUP BY (Orders.id_product,Product.name) ORDER BY amount DESC", nativeQuery = true)
    Collection<Map<String, Object>> getRankProducts();

    @Query(value = "SELECT Orders.id_product  AS id_product, Product.name AS name, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
            + "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill JOIN product Product ON Product.id_product=Orders.id_product "
            + "WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Orders.id_product,Product.name) ORDER BY amount DESC", nativeQuery = true)
    Collection<Map<String, Object>> getRankProductsByDate(@Param("startDate") Date startDate,
                                                          @Param("endDate") Date endDate);

    @Query(value = "SELECT Orders.id_product  AS id_product, Product.name AS name, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
            + "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill JOIN product Product ON Product.id_product=Orders.id_product "
            + "WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate AND Orders.id_product=:idProduct GROUP BY (Orders.id_product,Product.name) ORDER BY amount DESC LIMIT :limit ", nativeQuery = true)
    Collection<Map<String, Object>> getRankProductsByIdProductsAndDateAndLimit(@Param("idProduct") Long idProduct, @Param("limit") Integer limit, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT Orders.id_product  AS id_product, Product.name AS name, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
            + "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill JOIN product Product ON Product.id_product=Orders.id_product "
            + "WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate AND Orders.id_product=:idProduct GROUP BY (Orders.id_product,Product.name) ORDER BY amount DESC", nativeQuery = true)
    Collection<Map<String, Object>> getRankProductsByIdProductsAndDate(@Param("idProduct") Long idProduct, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT Orders.id_product  AS id_product, Product.name AS name, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
            + "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill JOIN product Product ON Product.id_product=Orders.id_product "
            + "WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Orders.id_product,Product.name) ORDER BY amount DESC LIMIT :limit ", nativeQuery = true)
    Collection<Map<String, Object>> getRankProductsByDateAndLimit(@Param("limit") Integer limit,
                                                                  @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT Orders.id_product  AS id_product, Product.name AS name, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
            + "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill JOIN product Product ON Product.id_product=Orders.id_product "
            + "WHERE Bill.status_bill=0 AND Orders.id_product=:idProduct GROUP BY (Orders.id_product,Product.name) ORDER BY amount DESC LIMIT :limit ", nativeQuery = true)
    Collection<Map<String, Object>> getRankProductsByIdProductsAndLimit(@Param("idProduct") Long idProduct,
                                                                        @Param("limit") Integer limit);

    @Query(value = "SELECT Orders.id_product  AS id_product, Product.name AS name, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
            + "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill JOIN product Product ON Product.id_product=Orders.id_product "
            + "WHERE Bill.status_bill=0  AND Orders.id_product=:idProduct GROUP BY (Orders.id_product,Product.name) ORDER BY amount DESC", nativeQuery = true)
    Collection<Map<String, Object>> getRankProductsByIdProducts(@Param("idProduct") Long idProduct);

    @Query(value = "SELECT Orders.id_product  AS id_product, Product.name AS name, SUM(COALESCE(Orders.amount,0)) AS amount, SUM(COALESCE(Orders.total,0)) AS total "
            + "FROM bill Bill JOIN orders Orders ON Orders.id_bill=Bill.id_bill JOIN product Product ON Product.id_product=Orders.id_product "
            + "WHERE Bill.status_bill=0 GROUP BY (Orders.id_product,Product.name) ORDER BY amount DESC LIMIT :limit ", nativeQuery = true)
    Collection<Map<String, Object>> getRankProductsByLimit(@Param("limit") Integer limit);


    //	SEARCH FOR BEST CLIENTS
    @Query(value = "SELECT Bill.id_user, User_app.username, SUM(COALESCE(Orders.total,0)) total " +
            "FROM orders Orders JOIN bill Bill on Bill.id_bill=Orders.id_bill JOIN user_app User_app on User_app.id_user=Bill.id_user " +
            "WHERE Bill.status_bill=0 GROUP BY (Bill.id_user,User_app.username)", nativeQuery = true)
    Collection<Map<String, Object>> getRankClients();

    @Query(value = "SELECT Bill.id_user, User_app.username, SUM(COALESCE(Orders.total,0)) total " +
            "FROM orders Orders JOIN bill Bill ON Bill.id_bill=Orders.id_bill JOIN user_app User_app on User_app.id_user=Bill.id_user " +
            "WHERE Bill.status_bill=0 " +
            "AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Bill.id_user,User_app.username)", nativeQuery = true)
    Collection<Map<String, Object>> getRankClientsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT Bill.id_user, User_app.username, SUM(COALESCE(Orders.total,0)) total " +
            "FROM orders Orders JOIN bill Bill ON Bill.id_bill=Orders.id_bill JOIN user_app User_app on User_app.id_user=Bill.id_user " +
            "WHERE Bill.status_bill=0 AND User_app.username=:username " +
            "GROUP BY (Bill.id_user,User_app.username)", nativeQuery = true)
    Collection<Map<String, Object>> getRankClientsByUsername(@Param("username") String username);

    @Query(value = "SELECT Bill.id_user, User_app.username, SUM(COALESCE(Orders.total,0)) total " +
            "FROM orders Orders JOIN bill Bill on Bill.id_bill=Orders.id_bill JOIN user_app User_app on User_app.id_user=Bill.id_user " +
            "WHERE Bill.status_bill=0 and User_app.username=:username "
            + "AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Bill.id_user,User_app.username)", nativeQuery = true)
    Collection<Map<String, Object>> getRankClientsByUsernameAndDate(@Param("username") String username, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //	TOTAL SALES
    @Query(value = "SELECT Orders.id_bill, SUM(COALESCE(Orders.total,0)) AS total FROM orders Orders "
            + "JOIN bill Bill on Bill.id_bill=Orders.id_bill WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Orders.id_bill) ORDER BY total DESC", nativeQuery = true)
    Collection<Map<String, BigInteger>> getSalesByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //	PAYMODE COUNT
    @Query(value = "select id_pay_mode,COUNT(id_pay_mode) quantity from bill WHERE Bill.status_bill=0 AND date BETWEEN :startDate AND :endDate GROUP BY (id_pay_mode)", nativeQuery = true)
    Collection<Map<String, BigInteger>> getSalesPayModeByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}