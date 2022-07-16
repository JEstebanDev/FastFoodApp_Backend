package JEstebanC.FastFoodApp.repository;

import JEstebanC.FastFoodApp.model.PriceAdditionalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 7/15/2022
 */
public interface IPriceAdditionalHistory extends JpaRepository<PriceAdditionalHistory,Long> {
    @Query(value = "SELECT * FROM price_additional_history WHERE creation_date<=:date and id_additional=:id_additional order by creation_date desc limit 1",nativeQuery = true)
    PriceAdditionalHistory findAdditionalHistoryByIdAdditionalAndDate(@Param("id_additional")Long id_additional, @Param("date") Date date);
}
