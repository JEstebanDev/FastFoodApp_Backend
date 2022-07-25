package JEstebanC.FastFoodApp.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 7/23/2022
 */
@Data
public class EntireUserDTO {
    private List<Integer> pages;
    private List<UserDTO> listUser;
}
