package SShop.mono.application.handler.exception;

import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
  private Integer businessErrorCode;

  private String businessExceptionDescription;

  private String error;

  private Set<String> validationErrors;

  private Map<String,String> errors;

}
