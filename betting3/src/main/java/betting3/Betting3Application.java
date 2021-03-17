package betting3;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import betting3.entity.AppUser;
import betting3.entity.Bet;
import betting3.entity.BetStatus;
import betting3.entity.BetType;
import betting3.repository.AppUserRepository;
import betting3.repository.BetRepository;

@SpringBootApplication
public class Betting3Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Betting3Application.class, args);
		
		AppUserRepository userRepository = ctx.getBean(AppUserRepository.class);
		BetRepository betRepository = ctx.getBean(BetRepository.class);
		
		
		if(!userRepository.findById(1l).isPresent()) {
			AppUser user = new AppUser();
			user.setId(1L);
			user.setFirstName("Sami");
			user.setLastName("Bet");
		userRepository.save(user);
		}
		
		if(betRepository.findAll().isEmpty()) {
		betRepository.saveAll(createBetList());
		}
		
	}

	
	private static List<Bet> createBetList (){
		
	     List<Bet> bets = new ArrayList<>();
			
			Bet a = new Bet();
			a.setDate(LocalDate.now());
			a.setSelection("fghjhg");
			a.setType(BetType.FOOTBALL);
			a.setOdd(2);
			a.setStatus(BetStatus.WON);
			Bet b = new Bet();
			b.setDate(LocalDate.now());
			b.setSelection("fghjhg");
			b.setType(BetType.FOOTBALL);
			b.setOdd(3);
			b.setStatus(BetStatus.LOSE);
			Bet c = new Bet();
			c.setDate(LocalDate.now());
			c.setSelection("fghjhg");
			c.setType(BetType.FOOTBALL);
			c.setOdd(6);
			c.setStatus(BetStatus.WON);
			Bet d = new Bet();
			d.setDate(LocalDate.now());
			d.setSelection("fghjhg");
			d.setType(BetType.FOOTBALL);
			d.setOdd(2);
			d.setStatus(BetStatus.LOSE);
			Bet e = new Bet();
			e.setDate(LocalDate.now());
			e.setSelection("fghjhg");
			e.setType(BetType.FOOTBALL);
			e.setOdd(11);
			e.setStatus(BetStatus.WON);
			Bet f = new Bet();
			f.setDate(LocalDate.now());
			f.setSelection("fghjhg");
			f.setType(BetType.FOOTBALL);
			f.setOdd(2);
			f.setStatus(BetStatus.WON);
			
			bets.add(a);
			bets.add(b);
			bets.add(c);
			bets.add(d);
			bets.add(e);
			bets.add(f);
			
			return bets;
		}
}
