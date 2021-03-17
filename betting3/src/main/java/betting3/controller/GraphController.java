package betting3.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import betting3.entity.Bet;
import betting3.repository.BetRepository;

@Controller
public class GraphController {
	
	@Autowired
	BetRepository betRepository;

	@GetMapping("/displayBarGraph")
	public String barGraph(Model model) {
		Map<String, Double> surveyMap = new LinkedHashMap<>();
		List<Bet> bets = betRepository.findAllBetsByOrderByDateDesc();
		
		for(Bet bet : bets) {
			surveyMap.put(bet.getDate().toString(), bet.getOdd());
			System.out.println(bet.getOdd());
		}
		
//		surveyMap.put("Java", 40);
//		surveyMap.put("Dev oops", 25);
//		surveyMap.put("Python", 20);
//		surveyMap.put(".Net", 15);
		model.addAttribute("surveyMap", surveyMap);
		return "barGraph";
	}

	@GetMapping("/displayPieChart")
	public String pieChart(Model model) {
		model.addAttribute("pass", 50);
		model.addAttribute("fail", 50);
		return "pieChart";
	}
	
	@GetMapping("/displayLineChart")
	public String lineChart(Model model) {
		
		
		Map<String, Double> surveyMap = new LinkedHashMap<>();
		List<Bet> bets = betRepository.findAllBetsByOrderByDateDesc();
		
		for(Bet bet : bets) {
			surveyMap.put(bet.getDate().toString(), bet.getOdd());
			System.out.println(bet.getOdd());
		}
		
		model.addAttribute("surveyMap", surveyMap);
		return "lineGraph";
		
	}

}
