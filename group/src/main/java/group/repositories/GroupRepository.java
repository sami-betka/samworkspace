package group.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import group.entities.Group;

public interface GroupRepository extends JpaRepository<Group, String>{

}
