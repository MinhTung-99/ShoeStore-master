package nguyen.shoestore.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Integer productId;
    private String productName;
    private double importPrice;
    private double Price;
    private Integer updaterId;
}
