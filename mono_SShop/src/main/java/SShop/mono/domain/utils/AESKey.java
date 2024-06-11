package SShop.mono.domain.utils;


import lombok.Data;

@Data
public class AESKey {
  private String key;
  private String iv;
}
