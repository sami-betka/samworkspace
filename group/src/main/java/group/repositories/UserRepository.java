package group.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import group.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
