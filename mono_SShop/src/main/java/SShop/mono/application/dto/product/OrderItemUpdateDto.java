package SShop.mono.application.dto.product;

import SShop.mono.domain.constant.OrderStatus;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemUpdateDto {
  private List<String> orderItemId;

  private OrderStatus status;
}
