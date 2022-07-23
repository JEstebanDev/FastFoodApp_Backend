package JEstebanC.FastFoodApp.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 7/22/2022
 */
@Data
public class EntireBillOrderDTO {
    private List<Integer> pages;
    private List<UserBillOrdersDTO> listBill;
}
