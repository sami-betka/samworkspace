package stock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import stock.entity.UserAccount;
import stock.repository.UserAccountRepository;

@Data
@Service
public class UserService {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	public UserAccount getByUserName (String userName) {
		UserAccount user = userAccountRepository.findByUserName(userName);
		return user;
	}

}
