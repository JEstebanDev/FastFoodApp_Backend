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
import java.text.SimpleDateFormat;
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
    @Query(value = "SELECT Bill.id_user, User_app.username,User_app.url_image, SUM(COALESCE(Orders.total,0)) total " +
            "FROM orders Orders JOIN bill Bill on Bill.id_bill=Orders.id_bill JOIN user_app User_app on User_app.id_user=Bill.id_user " +
            "WHERE Bill.status_bill=0 GROUP BY (Bill.id_user,User_app.username,User_app.url_image) ORDER BY(total) DESC LIMIT 5", nativeQuery = true)
    Collection<Map<String, Object>> getRankClients();

    @Query(value = "SELECT Bill.id_user, User_app.username,User_app.url_image, SUM(COALESCE(Orders.total,0)) total " +
            "FROM orders Orders JOIN bill Bill ON Bill.id_bill=Orders.id_bill JOIN user_app User_app on User_app.id_user=Bill.id_user " +
            "WHERE Bill.status_bill=0 " +
            "AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Bill.id_user,User_app.username,User_app.url_image) ORDER BY(total) DESC LIMIT 5", nativeQuery = true)
    Collection<Map<String, Object>> getRankClientsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT Bill.id_user, User_app.username,User_app.url_image, SUM(COALESCE(Orders.total,0)) total " +
            "FROM orders Orders JOIN bill Bill ON Bill.id_bill=Orders.id_bill JOIN user_app User_app on User_app.id_user=Bill.id_user " +
            "WHERE Bill.status_bill=0 AND User_app.username=:username " +
            "GROUP BY (Bill.id_user,User_app.username,User_app.url_image) ORDER BY(total) DESC LIMIT 5", nativeQuery = true)
    Collection<Map<String, Object>> getRankClientsByUsername(@Param("username") String username);

    @Query(value = "SELECT Bill.id_user, User_app.username,User_app.url_image, SUM(COALESCE(Orders.total,0)) total " +
            "FROM orders Orders JOIN bill Bill on Bill.id_bill=Orders.id_bill JOIN user_app User_app on User_app.id_user=Bill.id_user " +
            "WHERE Bill.status_bill=0 and User_app.username=:username "
            + "AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Bill.id_user,User_app.username,User_app.url_image) ORDER BY(total) DESC LIMIT 5", nativeQuery = true)
    Collection<Map<String, Object>> getRankClientsByUsernameAndDate(@Param("username") String username, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //	TOTAL SALES
    @Query(value = "SELECT Orders.id_bill, SUM(COALESCE(Orders.total,0)) AS total FROM orders Orders "
            + "JOIN bill Bill on Bill.id_bill=Orders.id_bill WHERE Bill.status_bill=0 AND Bill.date BETWEEN :startDate AND :endDate GROUP BY (Orders.id_bill) ORDER BY total DESC", nativeQuery = true)
    Collection<Map<String, BigInteger>> getSalesByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT EXTRACT(MONTH FROM date) AS month, SUM(COALESCE(total_price,0)) AS total FROM bill Bill WHERE Bill.status_bill=0 AND  " +
            "EXTRACT(MONTH FROM date) BETWEEN 1 AND EXTRACT(MONTH FROM CURRENT_DATE) GROUP BY (month) ORDER BY month ASC", nativeQuery = true)
    Collection<Map<String, Object>> getSalesMonthly();

    @Query(value = "SELECT distinct(extract(isodow from date)) as weekday, SUM(COALESCE(total_price,0)) AS total FROM bill Bill " +
            "WHERE Bill.status_bill=0 AND  Bill.date BETWEEN date_trunc('week', current_timestamp) AND  current_timestamp GROUP BY (weekday) ORDER BY weekday ASC", nativeQuery = true)
    Collection<Map<String, Object>> getSalesWeekly();
    //	PAYMODE COUNT
    @Query(value = "SELECT Bill.id_pay_mode,Pay.name, COUNT(Bill.id_pay_mode) quantity FROM bill Bill " +
            "JOIN pay_mode Pay ON Bill.id_pay_mode=Pay.id_pay_mode WHERE Bill.status_bill=0 GROUP BY (Bill.id_pay_mode,Pay.name) ORDER BY quantity DESC", nativeQuery = true)
    Collection<Map<String, Object>> getSalesPayMode();
}