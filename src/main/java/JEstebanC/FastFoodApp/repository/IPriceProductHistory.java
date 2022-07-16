package JEstebanC.FastFoodApp.repository;

import JEstebanC.FastFoodApp.model.PriceProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;


/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 7/15/2022
 */
@Repository
public interface IPriceProductHistory extends JpaRepository<PriceProductHistory,Long> {

    @Query(value = "SELECT * FROM price_product_history " +
            "WHERE creation_date<=:date and id_product=:id_product order by creation_date desc limit 1",nativeQuery = true)
    PriceProductHistory findProductHistoryByIdProductAndDate(@Param("id_product")Long id_product,@Param("date") Date date);
}
