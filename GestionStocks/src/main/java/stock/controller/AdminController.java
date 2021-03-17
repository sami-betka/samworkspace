package stock.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import stock.entity.Order;
import stock.entity.Product;
import stock.entity.UserAccount;
import stock.entity.UserRole;
import stock.repository.OrderRepository;
import stock.repository.ProductRepository;
import stock.repository.UserAccountRepository;
import stock.repository.UserRoleRepository;
import stock.utils.EncrytedPasswordUtils;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@GetMapping(value = "/administrate")
	public String GetAdminPage(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		navbarAttributes(model, principal);
		return "adminmenu";
	}

	@GetMapping(value = "/userlist")
	public String GetUserList(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		model.addAttribute("userlist", userAccountRepository.findAll());

		navbarAttributes(model, principal);
		return "userlist";
	}

	@GetMapping(value = "/productlist")
	public String GetProductList(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		model.addAttribute("productlist", productRepository.findByDeletedFalse());

		navbarAttributes(model, principal);
		return "productlist";
	}

	@RequestMapping(value = "/edit-user/{id}")
	public String editUser(@PathVariable("id") Long id, Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		UserAccount user = userAccountRepository.findById(id).get();

		model.addAttribute("user", user);

		navbarAttributes(model, principal);
		return "updateuser";

	}

	@PostMapping(value = "/update-user")
	public String updateUser(@Valid UserAccount user, BindingResult bindingresult, RedirectAttributes redirect) {
		if (bindingresult.hasErrors()) {
			redirect.addFlashAttribute("createsuccess", true);
			return "redirect:/admin/edit-user/" + user.getUserId();
		}

		user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(user.getEncrytedPassword()));
		userAccountRepository.save(user);
		return "redirect:/admin/userlist";

	}

	@RequestMapping(value = "/delete-user/{id}")
	public String deleteUser(@PathVariable("id") Long id, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		UserAccount user = userAccountRepository.findById(id).get();
		List<UserRole> userRoles = userRoleRepository.findAll();

		for (UserRole ur : userRoles) {
			if (ur.getUser().getUserId() == user.getUserId()) {
				userRoleRepository.delete(ur);
			}
		}

		userAccountRepository.deleteById(id);
		return "redirect:/admin/userlist";
	}

	// Admin
	// Ajouter un produit
	@GetMapping(value = "/add-product")
	public String addProduct(Model model, Principal principal) {

		model.addAttribute("emptyproduct", new Product());

		navbarAttributes(model, principal);
		return "addproduct";

	}

	@PostMapping(value = "/save-product")
	public String saveProduct(@Valid Product product, BindingResult bindingresult, RedirectAttributes redirect)
			throws IllegalStateException, IOException {

		if (bindingresult.hasErrors()) {
			redirect.addFlashAttribute("createsuccess", true);
			return "redirect:/admin/add-product";
		}

		productRepository.save(product);
		return "redirect:/admin/productlist";
	}
	// Admin
	// Modifier un produit

	@RequestMapping(value = "/edit-product/{id}")
	public String editProduct(@PathVariable("id") Long id, Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		Product product = productRepository.findById(id).get();

		model.addAttribute("product", product);

		navbarAttributes(model, principal);
		return "updateproduct";

	}

	@PostMapping(value = "/update-product")
	public String updateProduct(@Valid Product product, BindingResult bindingresult, Model model, Principal principal, RedirectAttributes redirect) {
		if (bindingresult.hasErrors()) {
			redirect.addFlashAttribute("createsuccess", true);

			return "redirect:/admin/edit-product/" + product.getId();
		}

		navbarAttributes(model, principal);
		productRepository.save(product);
		return "redirect:/admin/productlist";

	}
	// Admin
	// Supprimer un produit

	@RequestMapping(value = "/delete-product/{id}")
	public String deleteProduct(@PathVariable("id") Long id, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

	     Product p = productRepository.findById(id).get();
	     p.setDeleted(true);
	     productRepository.save(p);
	     
		return "redirect:/admin/productlist";
	}
	
	@RequestMapping(value = "/delete-order/{id}")
	public String deleteOrder(@PathVariable("id") Long id, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}
		UserAccount user = userAccountRepository.findByUserName(principal.getName());

		if (user == null) {
			return "redirect:/login";
		}
//		Order order = orderRepository.findById(id).get();
//		order.setUser(null);
//		user.getOrders().remove(order);
//		userAccountRepository.save(user);

		orderRepository.deleteById(id);
		return "redirect:/orders/orderlist";
	}

	// Other methods...
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
