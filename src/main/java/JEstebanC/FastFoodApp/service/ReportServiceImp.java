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
import java.math.BigDecimal;
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
    static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Collection<ReportClientDTO> getRankClient(String username, String startDate, String endDate) {
        log.info("Get the ranking of the best clients");
        boolean noConditionSearch = username == null && startDate == null && endDate == null;
        boolean usernameAndDateSearch = username != null && startDate != null && endDate != null;
        boolean dateSearch = username == null && startDate != null && endDate != null;
        boolean usernameSearch = username != null && startDate == null && endDate == null;

        if (noConditionSearch) {
            log.info("ReportServiceImp.java: Search without conditions");
            return reportRepository.getRankClients().stream().map(this::convertReportClientToDTO).collect(Collectors.toList());
        }

        if (dateSearch) {
            log.info("ReportServiceImp.java: Search with date");
            try {
                return reportRepository.getRankClientsByDate(new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(startDate), new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(endDate)).stream().map(this::convertReportClientToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                log.error("ReportServiceImp.java: Exception in getRankClient() when ranking clients by date");
                e.printStackTrace();
            }
        }
        if (usernameAndDateSearch) {
            log.info("ReportServiceImp.java: Search with username and date");
            try {
                return reportRepository.getRankClientsByUsernameAndDate(username, new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(startDate), new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(endDate)).stream().map(this::convertReportClientToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                log.error("ReportServiceImp.java: Exception in getRankClient()  ranking clients by username and date");
                e.printStackTrace();
            }
        }

        if (usernameSearch) {
            log.info("ReportServiceImp.java: Search with username");
            return reportRepository.getRankClientsByUsername(username).stream().map(this::convertReportClientToDTO).collect(Collectors.toList());

        }
        return null;
    }

    @Override
    public Collection<ReportProductDTO> getRankProducts(Long idProduct, Integer limit, String startDate, String endDate) {

        log.info("Get the ranking of the best products");
        boolean noConditionSearch = idProduct == null && limit == null && startDate == null && endDate == null;
        boolean idProductSearch = idProduct != null && limit == null && startDate == null && endDate == null;
        boolean limitSearch = idProduct == null && limit != null && startDate == null && endDate == null;
        boolean idProductAndLimitSearch = idProduct != null && limit != null && startDate == null && endDate == null;
        boolean limitAndDateSearch = idProduct == null && limit != null && startDate != null && endDate != null;
        boolean idProductAndDateSearch = idProduct != null && limit == null && startDate != null && endDate != null;
        boolean dateSearch = idProduct == null && limit == null && startDate != null && endDate != null;
        boolean idProductLimitAndDateSearch = idProduct != null && limit != null && startDate != null && endDate != null;

        if (noConditionSearch) {
            log.info("ReportService.java: Search without conditions");
            return reportRepository.getRankProducts().stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
        }

        if (idProductLimitAndDateSearch) {
            log.info("ReportService.java: Search by idProduct limit and date");
            try {
                return reportRepository.getRankProductsByIdProductsAndDateAndLimit(idProduct, limit, new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(startDate), new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(endDate)).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                log.error("ReportService.java: EXCEPTION in getRankProducts() when searching by idProduct, limit and date");
                e.printStackTrace();
            }
        }

        if (dateSearch) {
            log.info("ReportService.java: Search by date");
            try {
                return reportRepository.getRankProductsByDate(new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(startDate), new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(endDate)).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                log.error("ReportService.java: EXCEPTION in getRankProducts() when searching by date");
                e.printStackTrace();
            }
        }

        if (idProductAndDateSearch) {
            log.info("ReportService.java: Search by idProduct and date");
            try {
                return reportRepository.getRankProductsByIdProductsAndDate(idProduct, new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(startDate), new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(endDate)).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                log.error("ReportService.java: EXCEPTION in getRankProducts() when searching by idProduct and date");
                e.printStackTrace();
            }
        }

        if (limitAndDateSearch) {
            log.info("ReportService.java: Search by limit and date");
            try {
                return reportRepository.getRankProductsByDateAndLimit(limit, new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(startDate), new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(endDate)).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                log.error("ReportService.java: EXCEPTION in getRankProducts() when searching by limit and date");
                e.printStackTrace();
            }
        }

        if (idProductAndLimitSearch) {
            log.info("ReportService.java: Search by idProduct and limit");
            return reportRepository.getRankProductsByIdProductsAndLimit(idProduct, limit).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
        }

        if (idProductSearch) {
            log.info("ReportService.java: Search by idProduct");
            return reportRepository.getRankProductsByIdProducts(idProduct).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
        }

        if (limitSearch) {
            log.info("ReportService.java: Search by limit");
            return reportRepository.getRankProductsByLimit(limit).stream().map(this::convertReportProductToDTO).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Collection<ReportSalesDTO> getSalesByDate(String startDate, String endDate) {
        log.info("Get the sales");

        if (startDate != null && endDate != null) {
            log.info("ReportServiceImp.java: Search with date");
            try {
                return reportRepository.getSalesByDate(new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(startDate), new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(endDate)).stream().map(this::convertReportSalesToDTO).collect(Collectors.toList());
            } catch (ParseException e) {
                log.error("ReportServiceImp.java: Exception in getSalesByDate() while getting sales by date");
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
    public Collection<ReportPayModeDTO> getPayModeQuantity() {
        log.info("Get the pay mode");
        return reportRepository.getSalesPayMode().stream().map(this::convertReportPayModeToDTO).collect(Collectors.toList());
    }

    private ReportSalesDTO convertReportSalesToDTO(Map<String, BigInteger> sales) {
        ReportSalesDTO reportSales = new ReportSalesDTO();
        reportSales.setIdBill((long) sales.get("id_bill").intValue());
        reportSales.setTotal(sales.get("total"));
        return reportSales;
    }

    private ReportPayModeDTO convertReportPayModeToDTO(Map<String, Object> payMode) {
        ReportPayModeDTO reportPayMode = new ReportPayModeDTO();
        reportPayMode.setIdPayMode(((BigInteger) payMode.get("id_pay_mode")).intValue());
        reportPayMode.setName((String) payMode.get("name"));
        reportPayMode.setQuantity(((BigInteger) payMode.get("quantity")).intValue());
        return reportPayMode;
    }

    private ReportProductDTO convertReportProductToDTO(Map<String, Object> report) {
        ReportProductDTO reportProduct = new ReportProductDTO();
        reportProduct.setIdProduct((long) ((BigInteger) report.get("id_product")).intValue());
        reportProduct.setName(String.valueOf(report.get("name")));
        reportProduct.setAmount(((BigInteger) report.get("amount")).intValue());
        reportProduct.setTotal((BigInteger) report.get("total"));
        return reportProduct;
    }

    private ReportClientDTO convertReportClientToDTO(Map<String, Object> client) {
        ReportClientDTO reportClient = new ReportClientDTO();
        reportClient.setIdUser((long) ((BigInteger) client.get("id_user")).intValue());
        reportClient.setUsername((String) client.get("username"));
        reportClient.setUrlImage((String) client.get("url_image"));
        reportClient.setTotal(((BigInteger) client.get("total")));
        return reportClient;
    }

    private ReportSalesMonthlyDTO convertReportSalesMonthlyToDTO(Map<String, Object> salesMonthly) {
        ReportSalesMonthlyDTO reportSalesMonthly = new ReportSalesMonthlyDTO();
        reportSalesMonthly.setMonth((long) ((BigDecimal) salesMonthly.get("month")).intValue());
        reportSalesMonthly.setTotal(((BigInteger) salesMonthly.get("total")));
        return reportSalesMonthly;
    }

    private ReportSalesWeeklyDTO convertReportSalesWeeklyToDTO(Map<String, Object> salesWeekly) {
        ReportSalesWeeklyDTO reportSalesWeekly = new ReportSalesWeeklyDTO();
        reportSalesWeekly.setWeekday((long) ((BigDecimal) salesWeekly.get("weekday")).intValue());
        reportSalesWeekly.setTotal(((BigInteger) salesWeekly.get("total")));
        return reportSalesWeekly;
    }
}