package nguyen.shoestore.Dto;

import lombok.Getter;
import lombok.Setter;
import nguyen.shoestore.Entity.Item;
import nguyen.shoestore.Entity.User;

@Getter
@Setter
public class ResponseOrderDTO {
    private Integer codeOrder;
    private User user;
    private User staff;
    private Item item;
    private Integer quantity;
    private double money;
    private Integer process;
}
