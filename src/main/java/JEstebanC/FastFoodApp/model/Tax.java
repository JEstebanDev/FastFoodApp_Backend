package JEstebanC.FastFoodApp.model;

import JEstebanC.FastFoodApp.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 6/3/2022
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTax;
    @NotNull(message = "value cannot be empty or null")
    private double value;
}
