/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Client;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
public interface IClientService {

	Client create(Client client);

	Client update(Client client);

	Boolean delete(Long idClient);

	Collection<Client> list();

	Boolean exist(Long idClient);

}
