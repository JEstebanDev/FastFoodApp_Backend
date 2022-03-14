/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Subscriber;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-03-11
 */
public interface ISubscriberService {
	
	Subscriber create(Subscriber subscriber);
	
	Collection<Subscriber> list(Long page);

	Boolean delete(Long idSubscriber);

	Boolean exist(Long idSubscriber);

	Boolean existByEmail(String email);
	
	Collection<Subscriber> searchByEmail(String email);


}
