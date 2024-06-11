package SShop.mono.application.helpper.render;

import SShop.mono.application.utils.excel.ExcelConvertConfig;
import SShop.mono.application.utils.excel.ExcelConvertUtils;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;

public class ExcelRender<T> implements IExcelRender<T>, IRender<T> {

  private final ExcelRenderConfig config;

  public ExcelRender(ExcelRenderConfig config) {
    this.config = config;
  }

  public ExcelRender() {
    config = ExcelRenderConfig.builder().dateInputFormat("yyyy-MM-dd HH:mm:ss")
        .dateOutputFormat("yyyy-MM-dd HH:mm:ss").build();
  }

  @Override
  public void readExcel(InputStream inputStream, Class<T> classOfT, int batchSize,
      Consumer<List<T>> consumer) throws IOException {

    ExcelConvertConfig excelConvertConfig = new ExcelConvertConfig();
    excelConvertConfig.setSourceFile(inputStream);
    excelConvertConfig.setDateInputFormat(config.getDateInputFormat());
    excelConvertConfig.setFormatDate(new SimpleDateFormat(config.getDateOutputFormat()));

    ExcelConvertUtils<T> converter = new ExcelConvertUtils<T>(excelConvertConfig);
    converter.convert(classOfT, batchSize, consumer);
  }

  @Override
  public List<T> read(InputStream inputStream, Class<T> clazz) {
    return null;
  }
}
