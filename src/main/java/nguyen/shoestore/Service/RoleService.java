package nguyen.shoestore.Service;

import nguyen.shoestore.Dto.ResponseDTO;
import nguyen.shoestore.Entity.Role;
import nguyen.shoestore.Repository.RoleRepo;
import nguyen.shoestore.common.MessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
    public List<Role> getAllRole() {
        return roleRepo.findAll();
    }
    public Optional<Role> findRoleById(Integer roleId) {
        return roleRepo.findById(roleId);
    }
    public Role findRoleByName(String roleName){
        return roleRepo.getByRoleName(roleName);
    }
    @Transactional
    public ResponseDTO AddRole(String roleName){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            Role role = roleRepo.getByRoleName(roleName);
            Assert.isNull(role, MessageUtils.getMessage("error.notfound",roleName));
            roleRepo.save(role);
            responseDTO.setCode(1);
            responseDTO.setMessage("success");
            return responseDTO;
        }catch (IllegalArgumentException e){
            responseDTO.setCode(0);
            responseDTO.setMessage(e.getMessage());
            return responseDTO;
        }
    }
    @Transactional
    public ResponseDTO DeleteRole(Integer roleId) {
        ResponseDTO responseDTO = new ResponseDTO();
        Role role = roleRepo.getById(roleId);
        Assert.notNull(role, MessageUtils.getMessage("error.notfound",roleId));
        roleRepo.delete(role);
        responseDTO.setCode(1);
        responseDTO.setMessage("success");
        return responseDTO;
    }
}
