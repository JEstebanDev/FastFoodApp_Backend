package JEstebanC.FastFoodApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import JEstebanC.FastFoodApp.enumeration.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-22
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product_Additional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductAdditional;
    @ManyToOne
    @NotNull(message = "IdProduct cannot be empty or null")
    @JoinColumn(name = "IdAdditional")
    private Additional IdAdditional;

    @ManyToOne
    @NotNull(message = "IdProduct cannot be empty or null")
    @JoinColumn(name = "IdProduct")
    private Product IdProduct;

    private Status status;
}
