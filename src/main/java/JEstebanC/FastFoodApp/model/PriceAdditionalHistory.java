package JEstebanC.FastFoodApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 7/15/2022
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdditionalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPriceAdditionalHistory;
    @ManyToOne
    @NotNull(message = "idAdditional cannot be empty or null")
    @JoinColumn(name = "idAdditional")
    private Additional Additional;
    private int price;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date creationDate;
}
