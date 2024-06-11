package SShop.mono.application.dto;

import lombok.Data;

@Data

public class PublicKeyDTO {
  private String publicKey;
  public PublicKeyDTO(String publicKey) {
    this.publicKey = publicKey;
  }

}
