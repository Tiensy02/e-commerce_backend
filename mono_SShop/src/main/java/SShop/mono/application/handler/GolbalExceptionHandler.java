package SShop.mono.application.handler;

import static SShop.mono.application.handler.exception.BusinessErrorCodes.BAD_CREDENTIALS;

import SShop.mono.application.handler.exception.ExceptionResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

@RestControllerAdvice
public class GolbalExceptionHandler {

  @ExceptionHandler(LockedException.class)
  public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
    return ResponseEntity
        .status(HttpStatusCode.valueOf(404))
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(BAD_CREDENTIALS.getCode())
                .businessExceptionDescription(BAD_CREDENTIALS.getDescription())
                .error(exp.getMessage())
                .build()
        );
  }
}
