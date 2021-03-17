package betting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import betting.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	AppUser findByFirstName (String firstName);

}
