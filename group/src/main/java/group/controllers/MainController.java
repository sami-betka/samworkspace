package group.controllers;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group.entities.Group;
import group.entities.User;
import group.services.UserService;
import group.validators.UserValidator;

@RestController
public class MainController {
	
	UserService userService;
	UserValidator userValidator;
	
	 public MainController(UserService userService, UserValidator userValidator) {
		super();
		this.userService = userService;
		this.userValidator = userValidator;

	}

	@PostMapping(value= "/save-group")
	    public Group save(@RequestBody List<User> users, Errors errors) throws Exception {
		
		for(User user : users) {
			userValidator.validate(user, errors);
		}
		if(errors.hasErrors()) {
		
			throw new Exception("");
		}
		
		return userService.saveUsers( users);
	    }
	 	 
	
//	@PostMapping(value= "/save-group")
//    public Group saveGroup() {
//		
//		List<User> users2 = new ArrayList<>();
//		User user = new User();
//		
//		user.setAge(35);
//		user.setMail("sam@onepoint");
//		user.setPrenom("Sam");
//		
//		users2.add(user);
//
//	    return userService.saveUsers(users2);
//    }

}
