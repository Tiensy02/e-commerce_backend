package SShop.mono.application.dto.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

  @NotNull
  private String userId;

  @NotNull
  private String productId;

  @NotNull
  @Size(min = 1, max = 255)
  private String content;

  private String userAvatar;

  private String username;

  @Min(1)
  @Max(5)
  private int rating;
}
