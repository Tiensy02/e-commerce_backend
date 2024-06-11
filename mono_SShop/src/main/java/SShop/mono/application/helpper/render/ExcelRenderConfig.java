package SShop.mono.application.helpper.render;

import lombok.Data;

import lombok.Builder;

@Data
@Builder
public class ExcelRenderConfig {
  private String dateInputFormat;

  private String dateOutputFormat;
}
