package SShop.mono.controler.crypto;

import SShop.mono.application.dto.PublicKeyDTO;
import SShop.mono.domain.utils.FileUtils;
import SShop.mono.domain.utils.RSAUtils;
import java.io.File;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.util.Base64;
import javax.crypto.Cipher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class CryptoController implements CryptoAPI {
  private final FileUtils fileUtils;

  @Override
  public ResponseEntity<?> postSecretKey(String keyEncrypted) {
    File currentDir = new File("");
    // Lấy đường dẫn tuyệt đối của thư mục hiện tại
    String absolutePath = currentDir.getAbsolutePath();

    try{
      PrivateKey privateKey = RSAUtils.getPrivateKey(absolutePath + "/src/main/resources/private.txt");
      Cipher cipher =RSAUtils.getCipher(privateKey);
      String secretKey = RSAUtils.decrypt(keyEncrypted, cipher);
      try {
        fileUtils.saveStringToFile(secretKey, absolutePath + "/src/main/resources/secret.txt");
      } catch (Exception e){
        log.error("Error while saving the key");
        e.printStackTrace();
      }
      return ResponseEntity.status(202).body("");
    } catch (Exception e){
      log.error("Error while decrypting the key");
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Error while decrypting the key");
    }
  }

  @Override
  public ResponseEntity<?> getPublicKey() {
    try {
      File publicKeyFile = new File("./src/main/resources/public.txt");
      byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
      String publicKeyString = Base64.getEncoder().encodeToString(publicKeyBytes);
      return ResponseEntity.ok(publicKeyString);
    }
    catch (Exception e){
      log.error("Error while getting the public key");
      e.printStackTrace();
      return ResponseEntity.status(404).body("Error while getting the public key");
    }
  }
}
