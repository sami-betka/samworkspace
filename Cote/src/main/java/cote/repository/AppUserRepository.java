package cote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cote.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	AppUser findByFirstName (String firstName);

}
