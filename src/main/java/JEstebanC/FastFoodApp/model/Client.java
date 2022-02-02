package JEstebanC.FastFoodApp.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import JEstebanC.FastFoodApp.enumeration.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-22
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idClient;
    @NotNull(message = "name cannot be empty or null")
    @Column(length = 50)
    private String name;
    @NotNull(message = "username cannot be empty or null")
    @Column(length = 50)
    private String username;
    private String urlImage;
    @Column(length = 15)
    private int phone;
    @NotNull(message = "email cannot be empty or null")
    @Column(length = 50)
    private String email;
    @NotNull(message = "password cannot be empty or null")
    @Column(length = 30)
    private String password;
    private int discountPoint;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    private Collection<Role> role=new ArrayList<>();
    
    private Status status;
    
}
