package JEstebanC.FastFoodApp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
    private String date;

    @ManyToOne
    private Client idClient;

    @OneToOne
    private PayMode idPayMode;

    private Status status;
}
