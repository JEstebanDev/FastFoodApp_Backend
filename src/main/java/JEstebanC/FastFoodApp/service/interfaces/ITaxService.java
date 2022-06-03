package JEstebanC.FastFoodApp.service.interfaces;

import JEstebanC.FastFoodApp.model.Tax;

import java.util.Collection;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 6/3/2022
 */
public interface ITaxService {
    Tax create(Tax tax);

    Collection<Tax> read();

    Tax update(Long idTax, Tax tax);

    Boolean delete(Long idTax);

    Boolean exist(Long idTax);
}
