package nguyen.shoestore.Repository;

import nguyen.shoestore.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role getByRoleName (String roleName);
}
