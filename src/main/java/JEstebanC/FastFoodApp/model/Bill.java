package JEstebanC.FastFoodApp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import JEstebanC.FastFoodApp.enumeration.StatusBill;
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
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBill;
	private String idTransaction;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date date;
	@ManyToOne
	@NotNull(message = "idUser cannot be empty or null")
	@JoinColumn(name = "idUser")
	private User User;

	@OneToOne
	@NotNull(message = "idPayMode cannot be empty or null")
	@JoinColumn(name = "idPayMode")
	private PayMode PayMode;
	@NotNull(message = "noTable cannot be empty or null")
	private int noTable;
	@NotNull(message = "total price cannot be empty or null")
	private int totalPrice;
	@NotNull(message = "statusBill cannot be empty or null")
	private StatusBill statusBill;
}
