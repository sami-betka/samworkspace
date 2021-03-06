package betting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import betting.entity.AppUser;
import betting.repository.AppUserRepository;

@SpringBootApplication
public class CoteApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CoteApplication.class, args);

		AppUserRepository userRepository = ctx.getBean(AppUserRepository.class);

		if(!userRepository.findById(1l).isPresent()) {
			AppUser user = new AppUser();
			user.setFirstName("Sami");
			user.setLastName("Bet");

			userRepository.save(user);
		}
	}

}
