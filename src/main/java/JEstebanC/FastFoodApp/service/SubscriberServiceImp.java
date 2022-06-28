/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import JEstebanC.FastFoodApp.enumeration.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Subscriber;
import JEstebanC.FastFoodApp.repository.ISubscriberRepository;
import JEstebanC.FastFoodApp.service.interfaces.ISubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-11
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SubscriberServiceImp implements ISubscriberService {

	@Autowired
	private ISubscriberRepository subscriberRepository;

	public Subscriber create(String email) {
		log.info("Saving new subscriber: " + email);
		Subscriber subscriber=new Subscriber();
		subscriber.setEmail(email);
		subscriber.setStatus(Status.ACTIVE);
		return subscriberRepository.save(subscriber);
	}

	@Override
	public Collection<Subscriber> list(Long page) {
		log.info("List subscriber");
		return subscriberRepository.list(page);
	}

	@Override
	public Boolean delete(Long idSubscriber) {
		log.info("Delete subscriber with id: " + idSubscriber);
		subscriberRepository.deleteById(idSubscriber);
		return true;
	}

	@Override
	public Boolean exist(Long idSubscriber) {
		log.info("Searching subscriber with id: " + idSubscriber);
		return subscriberRepository.existsById(idSubscriber);
	}

	@Override
	public Boolean existByEmail(String email) {
		log.info("Searching subscriber with email: " + email);
		return subscriberRepository.findByEmail(email) != null;
	}
	
	@Override
	public Collection<Subscriber> searchByEmail(String email) {
		log.info("Searching subscriber with email: " + email);
		return subscriberRepository.findByEmailStartsWith(email);
	}

}
