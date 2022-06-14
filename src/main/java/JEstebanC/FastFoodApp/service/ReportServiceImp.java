/**
 *
 */
package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.dto.*;
import JEstebanC.FastFoodApp.repository.IReportRepository;
import JEstebanC.FastFoodApp.service.interfaces.IReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Collection<ReportClientDTO> getRankClient(String username, String startDate, String endDate) throws ParseException {
        log.info("Get the ranking of the best clients");

        if (username == null && startDate == null && endDate == null) {
            log.info("Search without conditions");
            return reportRepository.getRankClients().stream().map(this::convertReportClientToDTO).collect(Collectors.toList());
        }

        if (username == null && startDate != null && endDate != null) {
            log.info("Search with date");
            try {
                return reportRepository.getRankClientsByDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate)).stream().map(this::convertReportClientToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (username != null && startDate != null && endDate != null) {
            log.info("Search with username and date");
            try {
                return reportRepository.getRankClientsByUsernameAndDate(username, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate)).stream().map(this::convertReportClientToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (username != null && startDate == null && endDate == null) {
            log.info("Search with username");
            return reportRepository.getRankClientsByUsername(username).stream().map(this::convertReportClientToDTO).collect(Collectors.toList());

        }
        return null;
    }

    @Override
    public Collection<ReportProductDTO> getRankProducts(Long idProduct, Integer limit, String startDate, String endDate) {

        log.info("Get the ranking of the best products");

        if (idProduct == null && limit == null && startDate == null && endDate == null) {
            log.info("Search without conditions");
            return reportRepository.getRankProducts().stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
        }

        if (idProduct != null && limit != null && startDate != null && endDate != null) {
            log.info("Search by idProduct limit and date");
            try {
                return reportRepository.getRankProductsByIdProductsAndDateAndLimit(idProduct, limit, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate)).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (idProduct == null && limit == null && startDate != null && endDate != null) {
            log.info("Search by date");
            try {
                return reportRepository.getRankProductsByDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate)).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (idProduct != null && limit == null && startDate != null && endDate != null) {
            log.info("Search by idProduct and date");
            try {
                return reportRepository.getRankProductsByIdProductsAndDate(idProduct, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate)).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (idProduct == null && limit != null && startDate != null && endDate != null) {
            log.info("Search by limit and date");
            try {
                return reportRepository.getRankProductsByDateAndLimit(limit, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate)).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (idProduct != null && limit != null && startDate == null && endDate == null) {
            log.info("Search by idProduct and limit");

            return reportRepository.getRankProductsByIdProductsAndLimit(idProduct, limit).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());

        }
        if (idProduct != null && limit == null && startDate == null && endDate == null) {
            log.info("Search by idProduct");

            return reportRepository.getRankProductsByIdProducts(idProduct).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());

        }
        if (idProduct == null && limit != null && startDate == null && endDate == null) {
            log.info("Search by limit");

            return reportRepository.getRankProductsByLimit(limit).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());

        }
        return null;
    }

    @Override
    public Collection<ReportSalesDTO> getSalesByDate(String startDate, String endDate) {
        log.info("Get the sales");

        if (startDate != null && endDate != null) {
            log.info("Search with date");
            try {
                return reportRepository.getSalesByDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate)).stream().map(this::convertReportSalesToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Collection<ReportSalesMonthlyDTO> getSalesMonthly() {
        log.info("Get the sales monthly");
        return reportRepository.getSalesMonthly().stream().map(this::convertReportSalesMonthlyToDTO).collect(Collectors.toList());
    }

    @Override
    public Collection<ReportSalesWeeklyDTO> getSalesPerWeek() {
        log.info("Get the sales week");
        return reportRepository.getSalesWeekly().stream().map(this::convertReportSalesWeeklyToDTO).collect(Collectors.toList());
    }

    @Override
    public Collection<ReportPayModeDTO> getSalesPayModeByDate(String startDate, String endDate) {
        log.info("Get the paymode");
        if (startDate != null && endDate != null) {
            log.info("Search with date");
            try {
                return reportRepository.getSalesPayModeByDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate)).stream().map(this::convertReportPayModeToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    private ReportSalesDTO convertReportSalesToDTO(Map<String, BigInteger> sales) {
        ReportSalesDTO reportSales = new ReportSalesDTO();
        reportSales.setIdBill((long) sales.get("id_bill").intValue());
        reportSales.setTotal(sales.get("total").intValue());
        return reportSales;
    }

    private ReportPayModeDTO convertReportPayModeToDTO(Map<String, BigInteger> paymode) {
        ReportPayModeDTO reportPayMode = new ReportPayModeDTO();
        reportPayMode.setIdPayMode((long) paymode.get("id_pay_mode").intValue());
        reportPayMode.setQuantity(paymode.get("quantity").intValue());
        return reportPayMode;
    }

    private ReportProductDTO convertReportProductToDTO(Map<String, Object> report) {
        ReportProductDTO reportProduct = new ReportProductDTO();
        reportProduct.setIdProduct((long) ((BigInteger) report.get("id_product")).intValue());
        reportProduct.setName(String.valueOf(report.get("name")));
        reportProduct.setAmount(((BigInteger) report.get("amount")).intValue());
        reportProduct.setTotal(((BigInteger) report.get("total")).intValue());
        return reportProduct;
    }

    private ReportClientDTO convertReportClientToDTO(Map<String, Object> client) {
        ReportClientDTO reportClient = new ReportClientDTO();
        reportClient.setIdUser((long) ((BigInteger) client.get("id_user")).intValue());
        reportClient.setUsername((String) client.get("username"));
        reportClient.setUrlImage((String) client.get("url_image"));
        reportClient.setTotal(((BigInteger) client.get("total")).intValue());
        return reportClient;
    }

    private ReportSalesMonthlyDTO convertReportSalesMonthlyToDTO(Map<String, Object> salesMonthly) {
        ReportSalesMonthlyDTO reportSalesMonthly = new ReportSalesMonthlyDTO();
        reportSalesMonthly.setMonth((Double) salesMonthly.get("month"));
        reportSalesMonthly.setTotal(((BigInteger) salesMonthly.get("total")).intValue());
        return reportSalesMonthly;
    }

    private ReportSalesWeeklyDTO convertReportSalesWeeklyToDTO(Map<String, Object> salesWeekly) {
        ReportSalesWeeklyDTO reportSalesWeekly = new ReportSalesWeeklyDTO();
        reportSalesWeekly.setWeekday((Double) salesWeekly.get("weekday"));
        reportSalesWeekly.setTotal(((BigInteger) salesWeekly.get("total")).intValue());
        return reportSalesWeekly;
    }
}