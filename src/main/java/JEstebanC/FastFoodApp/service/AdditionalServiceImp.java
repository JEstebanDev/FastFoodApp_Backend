/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.repository.IAdditionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdditionalServiceImp implements IAdditionalService {

	@Autowired
	private IAdditionalRepository additionalRepository;

	@Override
	public Additional create(Additional additional) {
		log.info("Saving new additional: " + additional.getName());
		if (additional.getCategory() != null) {
			return additionalRepository.save(additional);
		} else {
			return null;
		}
	}

	@Override
	public Additional update(Long id, Additional additional) {
		log.info("Updating additional: " + additional.getName());
		if (additional.getCategory() != null) {
			return additionalRepository.save(additional);
		} else {
			return null;
		}
	}

	@Override
	public Boolean delete(Long id_additional) {
		log.info("Deleting the additional id: " + id_additional);
		if (additionalRepository.existsById(id_additional)) {
			additionalRepository.deleteById(id_additional);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Additional> list() {
		log.info("List all additional");
		return additionalRepository.findAll();
	}

	@Override
	public Boolean exist(Long id_additional) {
		log.info("Searching additional by id: " + id_additional);
		return additionalRepository.existsById(id_additional);
	}

	public Collection<Additional> findByName(String name) {
		log.info("Searching additional by name: " + name);
		return additionalRepository.findByNameStartsWith(name);
	}

}
