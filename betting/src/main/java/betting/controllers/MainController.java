package betting.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import betting.entity.Bet;
import betting.repository.AppUserRepository;

@Controller
public class MainController {

	@Autowired
	AppUserRepository userRepository;
	
	@GetMapping
	public String getHome (Model model) {
			  	        
		model.addAttribute("data", courbe(createBetList()));
		
		return "index";
	}
	
	
	@GetMapping("/test")
	public String getTest (Model model) {
				
		return "test";
	}
	
	@GetMapping("/index.html")
	public String getIndex (Model model) {
		
		model.addAttribute("data", courbe(createBetList()));
		model.addAttribute("user", userRepository.findById(1l).get());
		
		return "index";
	}
	
	@GetMapping("/buttons.html")
	public String getButtons () {
		
		return "buttons";
	}
	@GetMapping("/cards.html")
	public String getCards () {
		
		return "cards";
	}
	
	@GetMapping("/tables.html")
	public String getTables (Model model) {
		
		model.addAttribute("user", userRepository.findById(1l).get());

		return "tables";
	}
	
	@GetMapping("/charts.html")
	public String getChartsPage () {
		
		return "charts";
	}
	
	@GetMapping("/404.html")
	public String get404 () {
		
		return "404";
	}
	
	@GetMapping("/login.html")
	public String getLoginPage () {
		
		return "login";
	}
	
	@GetMapping("/register.html")
	public String getRegisterPage () {
		
		return "register";
	}
	
	@GetMapping("/forgot-password.html")
	public String getForgotPasswordPage () {
		
		return "forgot-password";
	}
	
	@GetMapping("/blank.html")
	public String getBlankPage () {
		
		return "blank";
	}
	
	@GetMapping("/utilities-animation.html")
	public String getAnimations () {
		
		return "utilities-animation";
	}
	
	@GetMapping("/utilities-border.html")
	public String getBorders () {
		
		return "utilities-border";
	}
	
	@GetMapping("/utilities-color.html")
	public String getColors () {
		
		return "utilities-color";
	}
	@GetMapping("/utilities-other.html")
	public String getOthers () {
		
		return "utilities-other";
	}
///////////////////////////////////////////////////////////////////////
	
	private Map<String, Double> courbe (List<Bet> bets) {
		
		 Map<String, Double> data = new LinkedHashMap<String, Double>();
	     data.put("Pari 0" , 0d);

		 double currentCote = 0;
		 

		 for(int i=0; i<bets.size(); i++) {
			
			 Bet bet = bets.get(i);
			 
			 
			 if(bet.isWon()==true ) {
				 
				 currentCote += bet.getCote();
			     data.put("Pari " + i+1, currentCote);
			 }else {
				 currentCote -= bet.getCote();
			     data.put("Pari " + i+1, currentCote);
			 }
			 
		}
		return data;
	}
	
	private List<Bet> createBetList (){
		
     List<Bet> bets = new ArrayList<>();
		
		Bet a = new Bet();
		a.setCote(2);
		a.setWon(true);
		Bet b = new Bet();
		b.setCote(2);
		b.setWon(true);
		Bet c = new Bet();
		c.setCote(2);
		c.setWon(false);
		Bet d = new Bet();
		d.setCote(2);
		d.setWon(false);
		Bet e = new Bet();
		e.setCote(2);
		e.setWon(true);
		
		bets.add(a);
		bets.add(b);
		bets.add(c);
		bets.add(d);
		bets.add(e);
		
		return bets;
	}
	
	
}
