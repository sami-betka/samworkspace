package group.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import group.entities.Group;
import group.entities.User;
import group.repositories.GroupRepository;
import group.repositories.UserRepository;

@Service
public class UserService {
	
	UserRepository userRepository;
	GroupRepository groupRepository;
	
	public UserService(UserRepository userRepository, GroupRepository groupRepository) {
		super();
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;

	}

		
	public Group saveUsers (List<User> users){
		
		Group group = new Group();
		group.setId(UUID.randomUUID().toString());
		group.setUsers(users);
		group.setCreationDate(LocalDateTime.now());
		
		userRepository.saveAll(users);
		
		return groupRepository.save(group);
	}

}
