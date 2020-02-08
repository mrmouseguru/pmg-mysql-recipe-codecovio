package guru.pmouse.recipe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by PMouse Guru  on 01/20/2020
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotfoundException extends RuntimeException {

    public NotfoundException(){
        super();
    }

    public NotfoundException(String message){
        super(message);
    }

    public NotfoundException(String message, Throwable cause){
        super(message, cause);
    }
}
