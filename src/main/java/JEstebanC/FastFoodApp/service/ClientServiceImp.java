/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Client;
import JEstebanC.FastFoodApp.repository.IClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImp implements IClientService {

	@Autowired
	private IClientRepository clientRepository;

	@Override
	public Client create(Client client) {
		log.info("Saving new client: " + client.getName());
		return clientRepository.save(client);
	}

	@Override
	public Client update(Client client) {
		log.info("Updating client with id: " + client.getIdClient());
		return clientRepository.save(client);
	}

	@Override
	public Boolean delete(Long idClient) {
		log.info("Deleting the client with id: " + idClient);
		if (clientRepository.existsById(idClient)) {
			clientRepository.deleteById(idClient);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Client> list() {
		log.info("List all clients");
		return clientRepository.findAll();
	}

	@Override
	public Boolean exist(Long idClient) {
		log.info("Searching client by id: " + idClient);
		return clientRepository.existsById(idClient);
	}

	public Collection<Client> findByName(String name) {
		log.info("Searching client by name: " + name);
		return clientRepository.findByName(name);
	}

	public Client findByEmail(String email) {
		log.info("Searching client by email: " + email);
		return clientRepository.findByEmail(email);
	}

}
