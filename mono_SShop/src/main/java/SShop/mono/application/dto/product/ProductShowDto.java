package SShop.mono.application.dto.product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductShowDto {

  private String id;

  private String name;

  private String imagePrimary;

  private String subTitle;

  private float price;

  private float discount;

  private float ratting;
}
