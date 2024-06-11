package SShop.mono.domain.utils;

import com.google.gson.Gson;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
@Component
public class AESUtils {
  @Value("${application.crypto.secret-file-path}")
  private String secretFilePath;
  public AESUtils() {

  }
  private Cipher getCipher(int mode) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, DecoderException, InvalidAlgorithmParameterException {


    File publicKeyFile = new File(secretFilePath);
    String secretKeyString = Files.readString(publicKeyFile.toPath());

    Gson gson = new Gson();

    AESKey keyAndIv = gson.fromJson(secretKeyString, AESKey.class);
    SecretKey key = new SecretKeySpec(
        Hex.decodeHex(keyAndIv.getKey().toCharArray()), "AES");
    AlgorithmParameterSpec iv = new IvParameterSpec(Hex.decodeHex(keyAndIv.getIv().toCharArray()));
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(mode, key, iv);
    return cipher;
  }
  public String decrypMess(String messEncrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, DecoderException {
    Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
    byte[] messbyte = Base64.getDecoder().decode(messEncrypted);
    byte[] messEn = cipher.doFinal(messbyte);
    String str = new String(messEn, "UTF-8");
    return str;
  }
  public String encriptionMess(String input) throws DecoderException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
    byte[] cipherTextBytes = cipher.doFinal(input.getBytes());
    String result = Base64.getEncoder().encodeToString(cipherTextBytes);
    return result;
  }
}


