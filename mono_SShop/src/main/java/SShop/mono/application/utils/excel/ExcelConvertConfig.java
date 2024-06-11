package SShop.mono.application.utils.excel;

import java.io.InputStream;
import java.text.DateFormat;
import lombok.Builder;
import lombok.Data;

@Data
public class ExcelConvertConfig {

  private InputStream sourceFile;

  private String fileName;

  private boolean writeToFile = false;

  private DateFormat formatDate = null;

  private String dateInputFormat;
}
