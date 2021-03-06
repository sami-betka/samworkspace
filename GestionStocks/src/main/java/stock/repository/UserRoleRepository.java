package stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.entity.UserAccount;
import stock.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
	List<UserRole> findByUser(UserAccount user);
	
	List<UserRole> deleteByUser (UserAccount user);

}
