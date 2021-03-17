package group.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import group.entities.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

		User user = (User) target;
		
		if(user.getAge() < 18) {
			errors.reject("age", "L'utilisateur n'est pas majeur");
		}

	}

}
