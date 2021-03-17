package betting3.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import betting3.entity.Bet;
import betting3.entity.BetStatus;
import betting3.repository.AppUserRepository;
import betting3.repository.BetRepository;

@Controller
public class MainController {

	@Autowired
	AppUserRepository userRepository;
	
	@Autowired
	BetRepository betRepository;
	
	@GetMapping
	public String getHome (Model model) {
			  	        
//		model.addAttribute("data", courbe(createBetList()));
		
		
		Map<Long, Double> surveyMap = new LinkedHashMap<>();
		List<Bet> bets = betRepository.findAllBetsByOrderByDateDesc();
		
		for(Bet bet : bets) {
			surveyMap.put(bet.getId(), bet.getOdd());
			System.out.println(bet.getOdd());
		}
		
		model.addAttribute("surveyMap", surveyMap);
//		return "lineGraph";
		
		
		
		return "dashboard";
	}
	
	
	@GetMapping("/test")
	public String getTest (Model model) {
				
		return "test";
	}
	
	@GetMapping("/index.html")
	public String getIndex (Model model) {
		
		model.addAttribute("data", betRepository.findAll());
		model.addAttribute("user", userRepository.findById(1l).get());
		
		System.out.println(betRepository.findAll().size());

		
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
		
//		List<Bet> betList = betRepository.findAll();
		
		model.addAttribute("user", userRepository.findById(1l).get());
		model.addAttribute("betList", betRepository.findAllBetsByOrderByIdAsc() );


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
	
	@GetMapping("/dashboard")
	public String getDashboardPage (Model model) {
		
//		model.addAttribute("data", courbe(createBetList()));
		
		
		Map<Long, Double> surveyMap = new LinkedHashMap<>();
		List<Bet> bets = betRepository.findAllBetsByOrderByDateDesc();
		
		for(Bet bet : bets) {
			surveyMap.put(bet.getId(), bet.getOdd());
			System.out.println(surveyMap.keySet().size());
//			System.out.println(bet.getOdd());
		}
		
		model.addAttribute("surveyMap", surveyMap);
//		return "lineGraph";
		
		
		
		return "dashboard";	}
	
	@GetMapping("/map.html")
	public String getMap () {
		
		return "map";
	}
	
	@GetMapping("/notifications.html")
	public String getNotifications () {
		
		return "notifications";
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
	@GetMapping("/user.html")
	public String getUser () {
		
		return "user";
	}
///////////////////////////////////////////////////////////////////////
	
	private Map<String, Double> courbe (List<Bet> bets) {
		
		 Map<String, Double> data = new LinkedHashMap<String, Double>();
	     data.put("Pari 0" , 0d);

		 double currentCote = 0;
		 

		 for(int i=0; i<bets.size(); i++) {
			
			 Bet bet = bets.get(i);
			 
			 
			 if(bet.getStatus() == BetStatus.WON) {
				 
				 currentCote += bet.getOdd();
			     data.put("Pari " + i+1, currentCote);
			 }
			 
			 if (bet.getStatus() == BetStatus.LOSE)
			 {
				 currentCote -= bet.getOdd();
			     data.put("Pari " + i+1, currentCote);
			 }
			 
		}
		return data;
	}
	
	private List<Bet> createBetList (){
		
     List<Bet> bets = new ArrayList<>();
		
		Bet a = new Bet();
		a.setOdd(2);
		a.setStatus(BetStatus.WON);
		Bet b = new Bet();
		a.setOdd(3);
		a.setStatus(BetStatus.LOSE);
		Bet c = new Bet();
		a.setOdd(6);
		a.setStatus(BetStatus.WON);
		Bet d = new Bet();
		a.setOdd(2);
		a.setStatus(BetStatus.LOSE);
		Bet e = new Bet();
		a.setOdd(11);
		a.setStatus(BetStatus.WON);
		Bet f = new Bet();
		a.setOdd(2);
		a.setStatus(BetStatus.WON);
		
		bets.add(a);
		bets.add(b);
		bets.add(c);
		bets.add(d);
		bets.add(e);
		bets.add(f);
		
		return bets;
	}
	
	
}
