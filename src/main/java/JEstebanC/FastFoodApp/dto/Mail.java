package JEstebanC.FastFoodApp.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 7/23/2022
 */
@Data
public class Mail {
    private String from;
    private String mailTo;
    private String subject;
    private List<Object> attachments;
    private Map<String, Object> props;
}
