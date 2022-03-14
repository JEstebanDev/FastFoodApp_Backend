/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.model.Bill;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {

	Bill findByIdBill(Long idBill);

	@Query(value = "SELECT id_bill, date, status_bill, id_pay_mode, id_user FROM bill WHERE id_user=:idUser AND status_bill=:statusBill AND date BETWEEN :startDate AND :endDate", nativeQuery = true)
	Collection<Bill> findByIdUserAndStatusBillAndDateBetween(@Param("idUser") Long idUser,
			@Param("statusBill") int statusBill, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT id_bill, date, status_bill, id_pay_mode, id_user FROM bill WHERE id_user=:idUser", nativeQuery = true)
	Collection<Bill> findByIdUser(@Param("idUser") Long idUser);

	Collection<Bill> findByStatusBill(StatusBill statusBill);

	Collection<Bill> findByDateBetween(Date startDate, Date endDate);

	@Query(value = "SELECT id_bill, date, status_bill, id_pay_mode, id_user FROM bill WHERE id_user=:idUser AND status_bill=:statusBill", nativeQuery = true)
	Collection<Bill> findByIdUserAndStatusBill(@Param("idUser") Long idUser, @Param("statusBill") int statusBill);

	@Query(value = "SELECT id_bill, date, status_bill, id_pay_mode, id_user FROM bill WHERE id_user=:idUser AND date BETWEEN :startDate AND :endDate", nativeQuery = true)
	Collection<Bill> findByDateBetweenAndIdUser(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("idUser") Long idUser);

	Collection<Bill> findByDateBetweenAndStatusBill(Date startDate, Date endDate, StatusBill statusBill);
}
