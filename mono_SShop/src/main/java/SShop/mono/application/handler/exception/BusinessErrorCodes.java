package SShop.mono.application.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCodes {
  BAD_CREDENTIALS(404, "Not found user", HttpStatus.BAD_REQUEST);

  @Getter
  private final int code;
  @Getter
  private final String description;
  @Getter
  private final HttpStatus httpStatus;

  BusinessErrorCodes(int code, String description, HttpStatus httpStatus) {
    this.code = code;
    this.description = description;
    this.httpStatus = httpStatus;
  }
}
