package SShop.mono.controler.config;


import SShop.mono.domain.utils.AESUtils;
import com.google.gson.Gson;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

    @ControllerAdvice

    public class ResponseConfig implements ResponseBodyAdvice<Object> {

      @Autowired
      AESUtils aesUtils;

      @Override
      public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
      Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

    HttpHeaders status = request.getHeaders();
    String valueEncryption =status.getValuesAsList("Encryption").size() > 0 ?  status.getValuesAsList("Encryption").get(0):"false";
    if(!valueEncryption.equals("true")) {
      return body;
    }else {
      Gson gson = new Gson();
      String str = "";
      if(body.getClass() != String.class) {
        str = gson.toJson(body);
      }
      else {
        str = (String) body;
      }
      try {
        String messEncrypted = aesUtils.encriptionMess(str);
        return messEncrypted;
      } catch (DecoderException e) {
        throw new RuntimeException(e);
      } catch (InvalidAlgorithmParameterException e) {
        throw new RuntimeException(e);
      } catch (NoSuchPaddingException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      } catch (InvalidKeyException e) {
        throw new RuntimeException(e);
      } catch (IllegalBlockSizeException e) {
        throw new RuntimeException(e);
      } catch (BadPaddingException e) {
        throw new RuntimeException(e);
      }
    }

  }
}
