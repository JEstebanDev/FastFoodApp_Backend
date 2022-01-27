package JEstebanC.FastFoodApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

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
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIngredient;
    @NotEmpty(message = "name cannot be empty or null")
    private String name;

    @ManyToOne
    @JoinColumn(name = "idCategoryIngredient")
    private CategoryIngredient idCategoryIngredient;

    private Status status;

}
