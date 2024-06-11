package SShop.mono.application.helpper.render;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public interface IExcelRender<T> {
  void readExcel(
      InputStream inputStream, Class<T> classOfT, int batchSize, Consumer<List<T>> consumer) throws IOException;

}
