package JEstebanC.FastFoodApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class CategoryAdditional {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCategoryAdditional;
    @NotNull(message = "name cannot be empty or null")
    @Column(length = 30)
    private String name;
    private Status status;

}
