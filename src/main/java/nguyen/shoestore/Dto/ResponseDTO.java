package nguyen.shoestore.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {
    private int code;
    private String message;
    private T response;
}
