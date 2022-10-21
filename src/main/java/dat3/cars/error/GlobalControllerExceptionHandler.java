package dat3.cars.error;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

//Experimental (not ready for production) meant to avoid stacktrace info in error messages

//@ControllerAdvice
class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private DefaultErrorAttributes defaultErrorAttributes;

  @SneakyThrows
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                           HttpHeaders headers, HttpStatus status, WebRequest request) {
    var def = defaultErrorAttributes;
    System.out.println(def);
    Map<String, Object> errorBody = new HashMap<>();

    if(HttpMessageNotReadableException.class.isInstance(ex)) {
      errorBody.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
      errorBody.put("status", "400");
      errorBody.put("message", "Could not handle supplied input, please report this error to vendor");
    } else{
      throw ex;
    }
    return new ResponseEntity<Object>(errorBody, HttpStatus.BAD_REQUEST);
  }

}