package JEstebanC.FastFoodApp.model;

import java.util.Date;

import javax.persistence.Entity;
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
	private Long idBill;
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

	private StatusBill statusBill;
}
