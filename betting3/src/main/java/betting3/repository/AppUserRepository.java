package betting3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import betting3.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	AppUser findByFirstName (String firstName);

}
