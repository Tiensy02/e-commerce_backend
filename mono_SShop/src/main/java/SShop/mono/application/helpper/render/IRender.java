package SShop.mono.application.helpper.render;

import java.io.InputStream;
import java.util.List;

public interface IRender<T> {
  List<T> read(InputStream inputStream, Class<T> clazz);

}
