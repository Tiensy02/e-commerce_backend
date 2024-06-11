package SShop.mono.domain.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class FileUtils {
  public File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = new File(file.getOriginalFilename());
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }

  public String generateFileName(MultipartFile multiPart) {
    return System.currentTimeMillis() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
  }
  public void saveStringToFile(String content, String path) {
    try (FileOutputStream fos = new FileOutputStream(path)) {
      fos.write(content.getBytes());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
