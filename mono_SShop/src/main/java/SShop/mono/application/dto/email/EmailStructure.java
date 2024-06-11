package SShop.mono.application.dto.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailStructure {
  private String from;

  private String[] to;

  private String subject;

  private String body;
}
