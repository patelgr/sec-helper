package app.scircle.ciklookup;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.net.URISyntaxException;

@ControllerAdvice
public class CikAdvice {

    @ResponseBody
    @ExceptionHandler(TickerNotFoundException.class)
    ProblemDetail employeeNotFoundHandler(TickerNotFoundException ex) throws URISyntaxException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        problemDetail.setTitle("Ticker Not Found");
        problemDetail.setType(new URI("https://www.scircle.app/tickerNotFound"));
        return problemDetail;
    }
}
