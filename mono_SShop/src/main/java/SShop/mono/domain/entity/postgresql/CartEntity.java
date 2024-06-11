package SShop.mono.domain.entity.postgresql;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "carts")
  public class CartEntity extends BaseEntity  {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String cartId;

    @Column(unique = true)
    private String userId;

    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    private List<CartItemEntity> cartItems;

    private int quantityItem;

}
