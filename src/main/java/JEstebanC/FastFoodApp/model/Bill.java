package JEstebanC.FastFoodApp.model;


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
public class Bill {

    @Id
    private Long idBill;
    private Date date;
    @ManyToOne
    @NotNull(message = "idUser cannot be empty or null")
    @JoinColumn(name = "idUser")
    private User User;

    @OneToOne
    @NotNull(message = "idPayMode cannot be empty or null")
    @JoinColumn(name = "idPayMode")
    private PayMode PayMode;

    private Status status;
}
