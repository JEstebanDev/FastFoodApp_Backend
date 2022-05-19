package JEstebanC.FastFoodApp.dto.payment;

import JEstebanC.FastFoodApp.model.Additional;
import lombok.Data;

import java.util.Collection;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Data
public class PaymentAdditionalInfoRequest {

    private Collection<PaymentItemRequest> items;
    private PaymentAdditionalInfoPayerRequest payer;
    private PaymentShipmentsRequest shipments;

}
