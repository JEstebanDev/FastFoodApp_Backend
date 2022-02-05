/**
 * 
 */
package JEstebanC.FastFoodApp.model;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import JEstebanC.FastFoodApp.enumeration.Status;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-01
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserApp;
	@NotNull(message = "description cannot be empty or null")
	private String userName;
	private String urlImage;
	private String email;
	private String password;
	@ManyToMany
	private Collection<Role> role = new ArrayList<>();
	private Status status;
}
