package JEstebanC.FastFoodApp.dto;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 6/13/2022
 */
@Data
public class ReportSalesMonthlyDTO {
    private Long month;
    private BigInteger total;
}
