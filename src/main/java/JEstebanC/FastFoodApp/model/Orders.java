package JEstebanC.FastFoodApp.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import JEstebanC.FastFoodApp.enumeration.StatusOrder;
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
	@NotNull(message = "idBill cannot be empty or null")
	@JoinColumn(name = "idBill")
	private Bill Bill;

	@ManyToOne
	@NotNull(message = "idProduct cannot be empty or null")
	@JoinColumn(name = "idProduct",updatable = false)
	private Product Product;

	@ManyToMany
	@JoinColumn(name = "idAdditional")
	@JoinTable(joinColumns = @JoinColumn(name = "idOrder"), 
	           inverseJoinColumns = @JoinColumn(name = "idAdditional"))
	private Collection<Additional> additional = new ArrayList<>();

	@NotNull(message = "amount cannot be empty or null")
	private int amount;
	@NotNull(message = "total cannot be empty or null")
	private int total;
	private StatusOrder statusOrder;

}
