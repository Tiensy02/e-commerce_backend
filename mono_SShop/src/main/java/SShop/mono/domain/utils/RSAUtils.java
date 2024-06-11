package SShop.mono.domain.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RSAUtils {

  public static void generateKey() throws NoSuchAlgorithmException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    KeyPair pair = generator.generateKeyPair();
    PrivateKey privateKey = pair.getPrivate();
    PublicKey publicKey = pair.getPublic();
    try (FileOutputStream fos = new FileOutputStream("./src/main/resources/public.txt")) {
      fos.write(publicKey.getEncoded());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try (FileOutputStream fos = new FileOutputStream("./src/main/resources/private.txt")) {
      fos.write(privateKey.getEncoded());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static PrivateKey getPrivateKey(String pathToPrivateKey)
      throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    File privateKeyFile = new File(pathToPrivateKey);
    byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
    EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
    return privateKey;
  }

  public static Cipher getCipher(PrivateKey privateKey)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException {
    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
    OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), PSource.PSpecified.DEFAULT);
    cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);
    return cipher;
  }
public static String decrypt(String encryptedMessage,Cipher cipher) {
    try {
      byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessage);
      byte[] decrypted = cipher.doFinal(encryptedMessageBytes);
      String message = new String(decrypted, StandardCharsets.UTF_8);
      return message;

    } catch (IllegalBlockSizeException e) {
      log.error("Lỗi giải mã RSA: ", e);
      throw new RuntimeException(e);
    } catch (BadPaddingException e) {
      log.error("Lỗi giải mã RSA: ", e);
      throw new RuntimeException(e);
    }
}

}
