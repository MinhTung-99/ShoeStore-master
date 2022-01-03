package nguyen.shoestore.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    private Integer itemId;
    private String productName;
    private String colorName;
    private int size;
    private int numItems;
    private String type;
    private int sale;
}
