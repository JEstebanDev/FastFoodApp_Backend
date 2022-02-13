/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.PayMode;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
public interface IPayModeService {

	PayMode create(PayMode payMode);

	PayMode update(Long id, PayMode payMode);

	Boolean delete(Long idPayMode);

	Collection<PayMode> list();

	Boolean exist(Long idPayMode);

}
