package JEstebanC.FastFoodApp.dto.payment;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Data
public class PaymentItemRequest {

    private Long id;
    private String title;
    private String description;
    private String pictureUrl;
    private String Category;
    private int quantity;
    private BigDecimal unitPrice;

}
