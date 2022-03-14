package JEstebanC.FastFoodApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import JEstebanC.FastFoodApp.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscriber {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idSubscriber;
	
	@NotNull(message = "email cannot be empty or null")
	private String email;
	
	private Status status;


}
