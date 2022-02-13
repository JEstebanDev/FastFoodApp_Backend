/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.dto.BillUserDTO;
import JEstebanC.FastFoodApp.dto.UserForBillDTO;
import JEstebanC.FastFoodApp.model.Bill;
import JEstebanC.FastFoodApp.model.PayMode;
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
	public BillUserDTO create(Bill bill) {
		log.info("Saving new bill with id: " + bill.getIdBill());
		return convertirBillToDTO(billRepository.save(bill));
	}

	@Override
	public BillUserDTO update(Long idBill,Bill bill) {
		log.info("Updating bill with id: " + bill.getIdBill());
		Bill billOld = billRepository.findByIdBill(idBill);
		billOld.setStatusBill(bill.getStatusBill());
		return convertirBillToDTO(billRepository.save(billOld));
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
	public Collection<BillUserDTO> list(String startDate, String endDate) {
		log.info("List all bills");
		try {
			return billRepository
					.findByDateBetween(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
							new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
					.stream().map(this::convertirBillToDTO).collect(Collectors.toList());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Boolean exist(Long idBill) {
		log.info("Searching bills by id: " + idBill);
		return billRepository.existsById(idBill);
	}

	public Collection<BillUserDTO> findByIdUser(Long idUser) {
		log.info("Searching bills by user called: " + idUser);
		if (userRepository.existsById(idUser)) {
			return billRepository.findByIdUser(idUser).stream().map(this::convertirBillToDTO)
					.collect(Collectors.toList());
		} else {
			return null;
		}

	}
	
	private BillUserDTO convertirBillToDTO(Bill bill) {
		BillUserDTO billUser = new BillUserDTO();
		billUser.setIdBill(bill.getIdBill());

		UserForBillDTO userForBill = new UserForBillDTO();
		userForBill.setIdUser(bill.getUser().getIdUser());
		userForBill.setUsername(bill.getUser().getUsername());
		userForBill.setName(bill.getUser().getName());

		billUser.setUserForBill(userForBill);

		PayMode payMode = new PayMode();
		payMode.setIdPayMode(bill.getPayMode().getIdPayMode());
		payMode.setName(bill.getPayMode().getName());
		payMode.setStatus(bill.getPayMode().getStatus());

		billUser.setPayMode(payMode);

		billUser.setDate(bill.getDate());
		billUser.setStatusBill(bill.getStatusBill());
		
		return billUser;
	}

	

}