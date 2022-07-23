package JEstebanC.FastFoodApp.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 7/22/2022
 */
@Data
public class EntireBillOrderDTO {
    private int page;
    private Page<UserBillOrdersDTO> listBill;
}
