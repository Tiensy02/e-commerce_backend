package SShop.mono.application.dto.product;

import com.google.gson.JsonArray;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
public class ProductDto {

  @NotNull
  private String name;

  @NotNull
  private String description;

  private String subTitle;

  @NotNull
  private float price;

  private List<String> images;

  private String categoryId;

  private Map<String,Object> customField;

  private float discount;

  private String imagePrimary;

  @NotNull
  private String userId;

  private float ratting;

  private int totalReview;

  @NotNull
  private int stock;

  private Map<String,String> chanelIds;
}
