package stock.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import stock.entity.Order;
import stock.entity.UserAccount;
import stock.repository.OrderRepository;
import stock.repository.UserAccountRepository;

@Controller
@RequestMapping(value = "/orders")
public class OrderController {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@GetMapping(value = "orderlist")
	public String getOrders (Model model, Principal principal) {
		
		if (principal == null) {
			return "redirect:/login";
		}
		UserAccount user = userAccountRepository.findByUserName(principal.getName());
		if (user == null) {
			return "redirect:/login";
		}
		
		if(orderRepository.findAll().isEmpty()) {
			return "redirect:/admin/administrate";

		}
		
		model.addAttribute("orderlist", orderRepository.findAll());
		navbarAttributes(model, principal);
		
		return "orderlist";
		
	}
	
	private void navbarAttributes(Model model, Principal principal) {

		if (principal != null) {

			UserAccount user = userAccountRepository.findByUserName(principal.getName());

			Optional<Order> optOrder = orderRepository.findByUserAndOpenTrue(user);

			if (optOrder.isPresent()) {

				Order order = optOrder.get();
				if (order.getProducts().isEmpty()) {
					model.addAttribute("quantity", 0);
				} else {
					model.addAttribute("quantity", order.getProducts().size());
				}
			} else {
				model.addAttribute("quantity", 0);

			}
			if (user != null) {
				model.addAttribute("user", user);
				model.addAttribute("ordersTrue", user.getOrders().stream().filter(o -> !o.isOpen()).count());
			} else {
				UserAccount us = new UserAccount();
				us.setUserName("");
				model.addAttribute("user", us);
			}
		}

	}

}
