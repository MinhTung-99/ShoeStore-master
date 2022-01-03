package nguyen.shoestore.Dto;

import lombok.Getter;
import lombok.Setter;
import nguyen.shoestore.common.Const;
import nguyen.shoestore.common.DataUtils;
import org.springframework.util.Assert;

@Getter
@Setter
public class UserDTO {
    private String userName;
    private String passWord;
    private String reTypePassword;
    private String fullName;
    private String gender;
    private String address;
    private String phoneNumber;
    private String email;
    private String role;
    private Integer status;


    public void validate(){
//        Assert.isTrue(DataUtils.matchByPattern(phoneNumber, Const.REGEX_PHONE_NUBBER),"Sai Đinh dạng");
        Assert.isTrue(DataUtils.matchByPattern(email, Const.REGEX_EMAIL),"Sai Đinh dạng");
        Assert.isTrue(reTypePassword.equals(passWord),"mật khảu nhập lại không đúng");
    }
}
