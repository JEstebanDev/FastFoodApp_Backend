/**
 * 
 */
package JEstebanC.FastFoodApp.service.interfaces;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import JEstebanC.FastFoodApp.dto.*;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-16
 */
public interface IReportService {

	Collection<ReportProductDTO> getRankProducts(Long idProduct,Integer limit, String startDate, String endDate);
	
	Collection<ReportClientDTO> getRankClient(String username,String startDate, String endDate) throws ParseException;
	
	Collection<ReportSalesDTO> getSalesByDate(String startDate, String endDate);

	Collection<ReportSalesMonthlyDTO> getSalesMonthly();

	Collection<ReportSalesWeeklyDTO> getSalesPerWeek() throws ParseException;
	Collection<ReportPayModeDTO> getSalesPayModeByDate(String startDate, String endDate);
}
