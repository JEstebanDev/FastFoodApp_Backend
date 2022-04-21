/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.dto.ReportClientDTO;
import JEstebanC.FastFoodApp.dto.ReportPayModeDTO;
import JEstebanC.FastFoodApp.dto.ReportProductDTO;
import JEstebanC.FastFoodApp.dto.ReportSalesDTO;
import JEstebanC.FastFoodApp.repository.IReportRepository;
import JEstebanC.FastFoodApp.service.interfaces.IReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-16
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImp implements IReportService {

	@Autowired
	private final IReportRepository reportRepository;

	@Override
	public Collection<ReportClientDTO> getRankClient(String startDate, String endDate) {
		log.info("Get the ranking of the best clients");

		if (startDate == null && endDate == null) {
			log.info("Search without conditions");
			return reportRepository.getRankClients().stream().map(this::convertirReportClientToDTO)
					.collect(Collectors.toList());
		}

		if (startDate != null && endDate != null) {
			log.info("Search with date");
			try {
				return reportRepository
						.getRankClientsByDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
						.stream().map(this::convertirReportClientToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Collection<ReportProductDTO> getRankProducts(Long idProduct, Integer limit, String startDate,
			String endDate) {

		log.info("Get the ranking of the best products");

		if (idProduct == null && limit == null && startDate == null && endDate == null) {
			log.info("Search without conditions");
			return reportRepository.getRankProducts().stream().map(this::convertirReportProductToDTO)
					.collect(Collectors.toList());
		}

		if (idProduct != null && limit != null && startDate != null && endDate != null) {
			log.info("Search by idProduct limit and date");
			try {
				return reportRepository
						.getRankProductsByIdProductsAndDateAndLimit(idProduct, limit,
								new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
						.stream().map(this::convertirReportProductToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (idProduct == null && limit == null && startDate != null && endDate != null) {
			log.info("Search by date");
			try {
				return reportRepository
						.getRankProductsByDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
						.stream().map(this::convertirReportProductToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (idProduct != null && limit == null && startDate != null && endDate != null) {
			log.info("Search by idProduct and date");
			try {
				return reportRepository
						.getRankProductsByIdProductsAndDate(idProduct,
								new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
						.stream().map(this::convertirReportProductToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (idProduct == null && limit != null && startDate != null && endDate != null) {
			log.info("Search by limit and date");
			try {
				return reportRepository
						.getRankProductsByDateAndLimit(limit, new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
						.stream().map(this::convertirReportProductToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (idProduct != null && limit != null && startDate == null && endDate == null) {
			log.info("Search by idProduct and limit");

			return reportRepository.getRankProductsByIdProductsAndLimit(idProduct, limit).stream()
					.map(this::convertirReportProductToDTO).collect(Collectors.toList());

		}
		if (idProduct != null && limit == null && startDate == null && endDate == null) {
			log.info("Search by idProduct");

			return reportRepository.getRankProductsByIdProducts(idProduct).stream()
					.map(this::convertirReportProductToDTO).collect(Collectors.toList());

		}
		if (idProduct == null && limit != null && startDate == null && endDate == null) {
			log.info("Search by limit");

			return reportRepository.getRankProductsByLimit(limit).stream().map(this::convertirReportProductToDTO)
					.collect(Collectors.toList());

		}
		return null;
	}

	@Override
	public Collection<ReportSalesDTO> getSalesByDate(String startDate, String endDate) {
		log.info("Get the sales");

		if (startDate != null && endDate != null) {
			log.info("Search with date");
			try {
				return reportRepository
						.getSalesByDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
						.stream().map(this::convertirReportSalesToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Collection<ReportPayModeDTO> getSalesPayModeByDate(String startDate, String endDate) {
		log.info("Get the paymode");

		if (startDate != null && endDate != null) {
			log.info("Search with date");
			try {
				return reportRepository
						.getSalesPayModeByDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
						.stream().map(this::convertirReportPayModeToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private ReportSalesDTO convertirReportSalesToDTO(Map<String, BigInteger> sales) {
		ReportSalesDTO reportSales = new ReportSalesDTO();
		reportSales.setIdBill((long) ((BigInteger) sales.get("id_bill")).intValue());
		reportSales.setTotal((Integer) ((BigInteger) sales.get("total")).intValue());
		return reportSales;
	}

	private ReportPayModeDTO convertirReportPayModeToDTO(Map<String, BigInteger> paymode) {
		ReportPayModeDTO reportPayMode = new ReportPayModeDTO();
		reportPayMode.setIdPayMode((long) ((BigInteger) paymode.get("id_pay_mode")).intValue());
		reportPayMode.setQuantity((Integer) ((BigInteger) paymode.get("quantity")).intValue());
		return reportPayMode;
	}

	private ReportProductDTO convertirReportProductToDTO(Map<String, BigInteger> report) {
		ReportProductDTO reportProduct = new ReportProductDTO();
		reportProduct.setIdProduct((long) ((BigInteger) report.get("id_product")).intValue());
		reportProduct.setAmount((Integer) ((BigInteger) report.get("amount")).intValue());
		reportProduct.setTotal((Integer) ((BigInteger) report.get("total")).intValue());
		return reportProduct;
	}

	private ReportClientDTO convertirReportClientToDTO(Map<String, BigInteger> client) {
		ReportClientDTO reportClient = new ReportClientDTO();
		reportClient.setIdUser((long) ((BigInteger) client.get("id_user")).intValue());
		reportClient.setTotal((Integer) ((BigInteger) client.get("total")).intValue());
		return reportClient;
	}

}