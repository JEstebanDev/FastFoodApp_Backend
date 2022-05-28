package JEstebanC.FastFoodApp.model;

import JEstebanC.FastFoodApp.enumeration.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-05-27
 */
@Entity
@Data
@Table(name = "Company")
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCompany;
    @NotNull(message = "name cannot be empty or null")
    private String name;
    private String urlImage;
    @NotNull(message = "nit code cannot be empty or null")
    private String nitCode;
    @NotNull(message = "address code cannot be empty or null")
    private String address;
    private String managerName;
    private Long phone;
    private Status status;
}
