package stock.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stock.entity.Order;
import stock.entity.UserAccount;
import stock.entity.UserRole;
import stock.repository.OrderRepository;
import stock.repository.ProductRepository;
import stock.repository.UserAccountRepository;
import stock.repository.UserRoleRepository;
import stock.utils.WebUtils;
 
@Controller
public class MainController {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	
@GetMapping(value = "/css")
public String testCss () {
	return "CssEffects";
}
 
    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model, Principal principal) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
//        navbarAttributes(model, principal);
//        return "welcomePage";
		return "redirect:/products/list";

    }
 
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
         
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
                 
        return "home";
    }
 
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model, Principal principal) {
    	
    	if(principal != null) {
    		return "redirect:/products/list";
    	}
 
        return "login";
    }
    
    @RequestMapping(value = "/userAccountInfo", method = RequestMethod.GET)
    public String loginSuccess(Model model, Principal principal) {
    	
    	UserAccount user = userAccountRepository.findByUserName(principal.getName());
    	for(UserRole userRole: userRoleRepository.findAll()) {
    		if(userRole.getAppRole().getRoleId() ==1 && userRole.getUser().getUserId()==user.getUserId()) {
    			return "redirect:/admingate";
    		}
    	}
		return "redirect:/products/list";
    }
    
    @GetMapping(value = "/admingate")
    public String accessGate (Model model, Principal principal) {
		
    	navbarAttributes(model, principal);
    	return "admingate";
    }
    
    @RequestMapping(value = "/administrate", method = RequestMethod.GET)
    public String adminGate(Model model, Principal principal) {
    	
    	navbarAttributes(model, principal);
 
        return "adminhome";
    }
 
    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model, Principal principal) {
        model.addAttribute("title", "Logout");
//        navbarAttributes(model, principal);
		return "redirect:/products/list";

//        return "logoutSuccessfulPage";
    }
 
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
 
        // After user login successfully.

		model.addAttribute("products", productRepository.findAll());

//      navbarAttributes(model, principal);
 
          return "home";
    }
 
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {

 
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
 
        }
 
        return "403Page";
    }
    
	
	private void navbarAttributes(Model model, Principal principal) {

		if (principal != null) {
			
			UserAccount user = userAccountRepository.findByUserName(principal.getName());
			
			model.addAttribute("user", user);

			Optional<Order> optOrder = orderRepository
					.findByUserAndOpenTrue(user);

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
			if(user != null) {
				model.addAttribute("ordersTrue", user.getOrders().stream().filter(o -> !o.isOpen()).count());
				}
		}
	}
 
}
