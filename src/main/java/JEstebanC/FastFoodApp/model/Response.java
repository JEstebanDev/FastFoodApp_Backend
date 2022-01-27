/**
 * 
 */
package JEstebanC.FastFoodApp.model;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */

@Data
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class Response {

    protected Instant timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map<?, ?> data;

}