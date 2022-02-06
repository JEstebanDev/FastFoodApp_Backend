/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Bill;
import JEstebanC.FastFoodApp.repository.IBillRepository;
import JEstebanC.FastFoodApp.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BillServiceImp implements IBillService {

	@Autowired
	private IBillRepository billRepository;
	@Autowired
	private IUserRepository userRepository;

	@Override
	public Bill create(Bill bill) {
		log.info("Saving new bill with id: " + bill.getIdBill());
		return billRepository.save(bill);
	}

	@Override
	public Bill update(Bill bill) {
		log.info("Updating bill with id: " + bill.getIdBill());
		return billRepository.save(bill);
	}

	@Override
	public Boolean delete(Long idBill) {
		log.info("Deleting the bill id: " + idBill);
		if (billRepository.existsById(idBill)) {
			billRepository.deleteById(idBill);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Bill> list() {
		log.info("List all bills");
		return billRepository.findAll();
	}

	@Override
	public Boolean exist(Long idBill) {
		log.info("Searching bills by id: " + idBill);
		return billRepository.existsById(idBill);
	}

	public Collection<Bill> findByIdUser(Long idUser) {
		log.info("Searching bills by user called: " + idUser);

		if (userRepository.existsById(idUser)) {

			return !billRepository.findByIdUser(idUser).isEmpty() ? billRepository.findByIdUser(idUser) : null;
		} else {
			return null;
		}
	}

}