package SShop.mono.controler.config;


import SShop.mono.domain.utils.AESUtils;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
public class RequestConfig implements RequestBodyAdvice {
  @Autowired
  AESUtils aesUtils;
  @Override
  public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
      MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException, IOException {

    HttpHeaders status = inputMessage.getHeaders();

    String valueEncryption =status.getValuesAsList("Encryption").size() > 0 ?  status.getValuesAsList("Encryption").get(0):"false";
    if(!valueEncryption.equals("true")){
      return inputMessage;
    }else {
      InputStream inputStream = inputMessage.getBody();
      byte[] buffer = new byte[1024];
      int bytesRead;
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        byteArrayOutputStream.write(buffer, 0, bytesRead);
      }
      String requestBodyString = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);

      System.out.println("body " + requestBodyString);

      inputStream.close();
      return new HttpInputMessage() {
        @Override
        public InputStream getBody() throws IOException {
          String mess = null;
          try {
            mess = aesUtils.decrypMess(requestBodyString);
          } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
          } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
          } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
          } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
          } catch (BadPaddingException e) {
            throw new RuntimeException(e);
          } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
          } catch (DecoderException e) {
            throw new RuntimeException(e);
          }
          byte[] bytes = mess.getBytes();
          return new ByteArrayInputStream(bytes);
        }

        @Override
        public HttpHeaders getHeaders() {
          HttpHeaders headers = new HttpHeaders();

          headers.add("Content-Type", "application/json");

          return headers;
        }
      };
    }

  }


  @Override
  public boolean supports(MethodParameter methodParameter,
      Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
      MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    return body;
  }

  @Override
  public Object handleEmptyBody(Object body, HttpInputMessage inputMessage,
      MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    return body;
  }
}
