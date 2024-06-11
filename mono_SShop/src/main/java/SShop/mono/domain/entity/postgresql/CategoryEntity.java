package SShop.mono.domain.entity.postgresql;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "category")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CategoryEntity extends BaseEntity {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String categoryName;

  private String categoryDescription;

  private String productIds;

  @ManyToOne
  @JoinColumn(name = "parent_category_id")
  @JsonIdentityReference(alwaysAsId = true)
  private CategoryEntity parentCategory;

  @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
  private List<CategoryEntity> subCategories;
}
