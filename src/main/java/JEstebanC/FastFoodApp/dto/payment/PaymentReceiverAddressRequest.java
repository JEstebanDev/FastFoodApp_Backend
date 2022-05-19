package JEstebanC.FastFoodApp.dto.payment;

import lombok.Data;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Data
public class PaymentReceiverAddressRequest {
    private String zipCode;
    private String stateName;
    private String cityName;
    private String streetName;
    private int streetNumber;
}
