package nguyen.shoestore.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Integer codeOrder;
    private Integer user;
    private Integer staff;
    private Integer itemId;
    private Integer quantity;
    private double money;
    private Integer process;
}
