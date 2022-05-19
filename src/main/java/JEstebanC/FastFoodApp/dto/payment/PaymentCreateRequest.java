package JEstebanC.FastFoodApp.dto.payment;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Data
public class PaymentCreateRequest {

    private Collection<PaymentAdditionalInfoRequest> additionalInfo;
    private String description;
    private String externalReference;
    private int installments;
    private String paymentMethodId;
    private BigDecimal transactionAmount;

}
