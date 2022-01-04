package nguyen.shoestore.Service;

import nguyen.shoestore.Dto.ResponseDTO;
import nguyen.shoestore.Dto.UserDTO;
import nguyen.shoestore.Entity.Role;
import nguyen.shoestore.Entity.User;
import nguyen.shoestore.Repository.UserRepo;
import nguyen.shoestore.common.DataUtils;
import nguyen.shoestore.common.MessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final TimeKeepingService TimeKeepingService;
    private final PurchaseOrderService purchaseOrderService;
    private final SalaryService salaryService;


    public UserService(UserRepo userRepo, RoleService roleService, TimeKeepingService timeKeepingService, PurchaseOrderService purchaseOrderService, SalaryService salaryService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        TimeKeepingService = timeKeepingService;
        this.purchaseOrderService = purchaseOrderService;
        this.salaryService = salaryService;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public List<User> getAllUsersActive() {
        return userRepo.findByAllActive();
    }
    public List<User> getAllCostumer(){
        return userRepo.getAllByRoleId(1);
    }
    public List<User> getAllAdmin(){
        return userRepo.getAllByRoleId(3);
    }
    public Optional<User> findUserById(Integer id) {
        return userRepo.findById(id);
    }
    public User findUserByUserName(String userName) {return userRepo.findByUserName(userName);}
    public List<User> findUserByFullName(String fullName) {
        return userRepo.getByFullName(fullName);
    }
    public List<User> findUserByGender(String gender) {
        return userRepo.getByGender(gender);
    }
    public List<User> findUserByAddress(String address) {
        return userRepo.getByAddress(address);
    }
    public User findUserByPhoneNumber(String phoneNumber){
        return userRepo.getByPhoneNumber(phoneNumber);
    }
    public User findUserByEmail(String email){
        return userRepo.getByEmail(email);
    }
    public List<User> findUserByRole(String role){
        return userRepo.findByRoles(role);
    }
    @Transactional
    public ResponseDTO AddUser(UserDTO userDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        User user = new User();
        Assert.notNull(userDTO, MessageUtils.getMessage("error.input.null", userDTO));
        Role role = roleService.findRoleByName(userDTO.getRole());
        Assert.notNull(role, MessageUtils.getMessage("error.notfound", userDTO.getRole()));
        user.setRoleId(role.getRoleId());
        Assert.notNull(role, MessageUtils.getMessage("error.notfound", userDTO.getRole()));
        User user1 = userRepo.findByUserName(userDTO.getUserName());
        Assert.isNull(user1, MessageUtils.getMessage("username.not.valid", userDTO.getUserName()));
        user1 = userRepo.findByEmail(userDTO.getEmail());
        Assert.isNull(user1, MessageUtils.getMessage("success.found", userDTO.getEmail()));
        user1 = userRepo.findByPhoneNumber(userDTO.getPhoneNumber());
        Assert.isNull(user1, MessageUtils.getMessage("success.found", userDTO.getPhoneNumber()));
        user.setUserName(userDTO.getUserName());
        user.setPassWord(userDTO.getPassWord());
        user.setFullName(userDTO.getFullName());
        user.setGender(userDTO.getGender());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setStatus(1);
        userRepo.save(user);
        responseDTO.setCode(1);
        responseDTO.setMessage("success");
        return responseDTO;
    }
    @Transactional
    public ResponseDTO DeleteUser(Integer id) {
        ResponseDTO responseDTO = new ResponseDTO();
        User user = userRepo.getById(id);
        Assert.notNull(user, MessageUtils.getMessage("error.notfound",id));
        user.setStatus(0);
        userRepo.save(user);
        responseDTO.setCode(1);
        responseDTO.setMessage("success");
        return responseDTO;
    }
    @Transactional
    public ResponseDTO updateUser(UserDTO userDTO, String emailRs){
        ResponseDTO responseDTO = new ResponseDTO();
        Assert.notNull(userDTO, MessageUtils.getMessage("error.input.null", userDTO));
        User user = userRepo.findByEmail(emailRs);
        Assert.notNull(user, MessageUtils.getMessage("error.notfound", userDTO.getEmail()));
        if(user.getPassWord().equals(userDTO.getPassWord())){
            user.setPassWord(userDTO.getPassWord());
            user.setFullName(userDTO.getFullName());
            user.setGender(userDTO.getGender());
            user.setAddress(userDTO.getAddress());
            user.setStatus(1);
            userRepo.save(user);
            responseDTO.setCode(1);
            responseDTO.setMessage("success");
        }else {
            System.out.printf("sai  mat khau");
            responseDTO.setCode(0);
            responseDTO.setMessage("sai mat khau");
        }
        return responseDTO;
    }
}
