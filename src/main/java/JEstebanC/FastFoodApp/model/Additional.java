package JEstebanC.FastFoodApp.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
public class Additional {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAdditional;
    @NotNull(message = "name cannot be empty or null")
    @Column(length = 30)
    private String name;
    private String imageUrl;
    @NotNull(message = "name cannot be empty or null")
    private int price;
    
    @ManyToMany
	@JoinColumn(name = "idCategory")
	@JoinTable(joinColumns = @JoinColumn(name = "idAdditional"), 
	           inverseJoinColumns = @JoinColumn(name = "idCategory"))
	private Collection<Category> category = new ArrayList<>();

    private Status status;

}
