/**
 *
 */
package JEstebanC.FastFoodApp.repository;

import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {

    Bill findByIdBill(Long idBill);

    @Query(value = "SELECT Bill.* FROM bill Bill JOIN user_app User_App on Bill.id_user=User_App.id_user " +
            "WHERE User_App.username=:username AND Bill.status_bill=:statusBill AND Bill.date BETWEEN :startDate AND :endDate", nativeQuery = true)
    Collection<Bill> findByIdUserAndStatusBillAndDateBetween(@Param("username") String username,
                                                             @Param("statusBill") int statusBill, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT Bill.* FROM bill Bill JOIN user_app User_App on Bill.id_user=User_App.id_user WHERE User_App.username=:username", nativeQuery = true)
    Collection<Bill> findByIdUser(@Param("username") String username);

    Collection<Bill> findByStatusBill(StatusBill statusBill);

    Collection<Bill> findByDateBetween(Date startDate, Date endDate);

    @Query(value = "SELECT Bill.* FROM bill Bill JOIN user_app User_App on Bill.id_user=User_App.id_user WHERE User_App.username=:username AND Bill.status_bill=:statusBill", nativeQuery = true)
    Collection<Bill> findByIdUserAndStatusBill(@Param("username") String username, @Param("statusBill") int statusBill);

    @Query(value = "SELECT Bill.* FROM bill Bill JOIN user_app User_App on Bill.id_user=User_App.id_user WHERE User_App.username=:username AND Bill.date BETWEEN :startDate AND :endDate", nativeQuery = true)
    Collection<Bill> findByDateBetweenAndIdUser(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                                @Param("username") String username);

    Collection<Bill> findByDateBetweenAndStatusBill(Date startDate, Date endDate, StatusBill statusBill);

    @Query(value = "SELECT distinct(bi.*) FROM bill bi join orders ord on ord.id_bill=bi.id_bill where date BETWEEN :startDate AND :endDate and ord.status_order=:statusOrder", nativeQuery = true)
    Collection<Bill> findByDateBetweenAndStatusOrder(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                                     @Param("statusOrder") int statusOrder);

}
