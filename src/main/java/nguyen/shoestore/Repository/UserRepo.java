package nguyen.shoestore.Repository;

import nguyen.shoestore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
    @Query("select u from User u where u.status =  1")
    List<User> findByAllActive();
    List<User> getByFullName (String fullName);
    @Query("select u from User u where u.roleId=1 and u.status =1 and u.fullName =: csName")
    User getByCostumersName(String csName);
    @Query("select u from User u where  u.roleId = 2  and u.status = 1 and u.fullName =: sfName")
    User getByStaffsName(String sfName);
    List<User> getByGender (String gender);
    List<User> getByAddress (String address);
    User getByPhoneNumber (String phoneNumber);
    User getByEmail (String email);
    User findByEmail (String email);
    User findByPhoneNumber (String phoneNumber);
    @Query("select u from User u where u.roleId in (SELECT roleId FROM Role where roleName =: role)")
    List<User> findByRoles (String role);
}
