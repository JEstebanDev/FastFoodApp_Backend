package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.PayMode;
import JEstebanC.FastFoodApp.repository.IPayModeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PayModeServiceImp implements IPayModeService {

	@Autowired
	private final IPayModeRepository payModeRepository;

	@Override
	public PayMode create(PayMode payMode) {
		log.info("Saving new category: " + payMode.getName());
		return payModeRepository.save(payMode);
	}

	@Override
	public PayMode update(PayMode payMode) {
		log.info("Updating category: " + payMode.getName());
		return payModeRepository.save(payMode);
	}

	@Override
	public Boolean delete(Long idPayMode) {
		log.info("Deleting the category id: " + idPayMode);
		if (payModeRepository.existsById(idPayMode)) {
			payModeRepository.deleteById(idPayMode);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<PayMode> list() {
		log.info("List all categories");
		return payModeRepository.findAll();
	}

	@Override
	public Boolean exist(Long idPayMode) {
		log.info("Searching category by id: " + idPayMode);
		return payModeRepository.existsById(idPayMode);
	}

}
