package stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.entity.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
	
	UserAccount findByUserName(String userName);

}
