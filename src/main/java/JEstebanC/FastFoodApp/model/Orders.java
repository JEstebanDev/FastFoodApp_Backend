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
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idOrder;

    @ManyToOne
    @NotNull(message = "IdBill cannot be empty or null")
    @JoinColumn(name = "IdBill")
    private Bill IdBill;

    @ManyToOne
    @NotNull(message = "IdProduct cannot be empty or null")
    @JoinColumn(name = "IdProduct")
    private Product IdProduct;
    
    @NotNull(message = "amount cannot be empty or null")
    private int amount;
    private int price;

    private Status status;

}
