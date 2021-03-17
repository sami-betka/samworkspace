package stock.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import stock.entity.Mail;
import stock.entity.Order;
import stock.entity.Product;
import stock.entity.Product2;
import stock.entity.UserAccount;
import stock.entity.UserRole;
import stock.repository.AppRoleRepository;
import stock.repository.OrderRepository;
import stock.repository.ProductRepository;
import stock.repository.UserAccountRepository;
import stock.repository.UserRoleRepository;
import stock.repository.resultset.OrdersProductsRs;
import stock.service.impl.MailService;
import stock.service.impl.OrderService;
import stock.service.impl.ProductService;
import stock.service.impl.UserService;
import stock.utils.EncrytedPasswordUtils;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	EntityManager entityManager;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	AppRoleRepository appRoleRepository;

	@Autowired
	OrderService orderService;

	@Autowired
	UserService userService;

	@Autowired
	ProductService productService;
	
	@Autowired
	MailService mailService;

	@Autowired
	static EncrytedPasswordUtils encrytedPasswordUtils;
	
	@GetMapping(value = "/userinfos")
	public String getUserInfos(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		UserAccount user = userAccountRepository.findByUserName(principal.getName());
		if (user == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("user", user);
		navbarAttributes(model, principal);
		
		
		return "myinfos";
		
	}
	
	@GetMapping(value = "/payment")
	public String goToPayment (Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		UserAccount user = userAccountRepository.findByUserName(principal.getName());
		if (user == null) {
			return "redirect:/login";
		}
		navbarAttributes(model, principal);
		return "payment";
		}
	
	@GetMapping("/sendmail")
	public String sendMail(Model model, Principal principal) {

		UserAccount user = userAccountRepository.findByUserName(principal.getName());

		Mail mail = new Mail();
		mail.setMailFrom("samtetedestup@gmail.com");
		mail.setMailTo(user.getEmail());
		mail.setMailSubject("Confirmation de commande");
		mail.setMailContent("Commande confirmée");
		mailService.sendEmail(mail, principal);

		navbarAttributes(model, principal);

		return "mailconfirmation";

	}

//	@GetMapping
//	public String home(Model model, Principal principal) {
//
//		navbarAttributes(model, principal);
//		return "home";
////		return "index";
//
//	}

	// Ajouter un utilisateur

	@GetMapping(value = "/create-account")
	public String addUser(Model model, Principal principal) {

		model.addAttribute("emptyUser", new UserAccount());

		navbarAttributes(model, principal);
		return "register";
	}

	@PostMapping("/save-user")
	public String saveUser(@Valid UserAccount user, BindingResult result, Model model, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addFlashAttribute("createsuccess", true);
			return "redirect:/users/create-account";
		}
		user.setEncrytedPassword(encrytedPasswordUtils.encrytePassword(user.getEncrytedPassword()));
		user.setOrders(new ArrayList<Order>());

		UserRole userRole = new UserRole(user, appRoleRepository.findById(2L).get());

		userAccountRepository.save(user);
		userRoleRepository.save(userRole);

		return "redirect:/products/list";
	}
	
	
	
	@RequestMapping(value = "/edit-myinfos")
	public String editUser(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		UserAccount user = userAccountRepository.findByUserName(principal.getName());

		model.addAttribute("user", user);

		navbarAttributes(model, principal);
		return "updatemyinfos";

	}

	@PostMapping(value = "/update-myinfos")
	public String updateUser(@Valid UserAccount user, Model model, BindingResult bindingresult, Principal principal,RedirectAttributes redirect) {
		if (bindingresult.hasErrors()) {
			redirect.addFlashAttribute("createsuccess", true);

			return "redirect:/users/edit-myinfos";
		}
		
		if (principal == null) {
			return "redirect:/login";
		}
//		UserAccount user = userAccountRepository.findByUserName(principal.getName());
//		if (user == null) {
//			return "redirect:/login";
//		}
		
		model.addAttribute("user", user);
		navbarAttributes(model, principal);
		
		user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(user.getEncrytedPassword()));
			userAccountRepository.save(user);
			return "myinfos";
		
	}

	// Ajouter un produit à ma commande

	@GetMapping("/add-to-order/{productId}")
	public String addProductToMyOrder(@PathVariable("productId") Long productId, RedirectAttributes redirect, Principal principal, Model model,
			@RequestParam(name = "page", defaultValue = "0") int p) {

		if (principal == null) {
			return "redirect:/login";
		}
		UserAccount user = userAccountRepository.findByUserName(principal.getName());

		if (user == null) {
			return "redirect:/login";
		}
		orderService.addProductToOrder(productId, user);

		navbarAttributes(model, principal);
//		String msg = "Article ajouté à votre commande";
        redirect.addFlashAttribute("addsuccess", true);
   
		return "redirect:/products/list?page=" + p ;
	}

	@GetMapping("/add-to-order2/{productId}")
	public String addProductToMyOrder2(@PathVariable("productId") Long productId, Principal principal, Model model) {

		if (principal == null) {
			return "redirect:/login";
		}
		UserAccount user = userService.getByUserName(principal.getName());

		if (user == null) {
			return "redirect:/login";
		}

		Product product = productService.getById(productId).get();
		Optional<Order> optOrder = orderService.getByUserAndOpenTrue(user);

		if (optOrder.isPresent()) {

			Order order = optOrder.get();

			orderService.addProductToMyOrder2(user, order, product);

			BigDecimal total = orderService.calculateTotalPrice(order);

			model.addAttribute("total", total);
			model.addAttribute("order", order);
			model.addAttribute("orderproducts", checkInStockOrderDetails(order));
			navbarAttributes(model, principal);

			return "myOrder";
		}

		return "redirect:/products/list/";
	}
	
	@GetMapping("/add-to-order3/{productId}")
	public String addProductToMyOrder3(@PathVariable("productId") Long productId, Principal principal, Model model) {

		if (principal == null) {
			return "redirect:/login";
		}
		UserAccount user = userService.getByUserName(principal.getName());

		if (user == null) {
			return "redirect:/login";
		}

		Product product = productService.getById(productId).get();
		Optional<Order> optOrder = orderService.getByUserAndOpenTrue(user);

//		if (optOrder.isPresent()) {

//			Order order = optOrder.get();

			orderService.addProductToOrder(productId,user );

//			BigDecimal total = orderService.calculateTotalPrice(order);

//			model.addAttribute("total", total);
//			model.addAttribute("order", order);
//			model.addAttribute("orderproducts", checkInStockOrderDetails(order));
			navbarAttributes(model, principal);

			return "redirect:/products/details/" + productId;
//		}

//		return "redirect:/products/list";
	}

	@GetMapping("/me/order/remove/{productId}")
	public String removeFromOrder(@PathVariable("productId") Long productId, Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		UserAccount user = userAccountRepository.findByUserName(principal.getName());
		Product product = productRepository.findById(productId).get();
		Optional<Order> optOrder = orderRepository.findByUserAndOpenTrue(user);

		if (optOrder.isPresent()) {

			Order order = optOrder.get();

			user.getOrders().remove(order);
			order.getProducts().remove(product);
			order.setNum("En attente de validation");
			user.getOrders().add(order);

			orderRepository.save(order);
			userAccountRepository.save(user);

			BigDecimal total = BigDecimal.ZERO;

			for (Product p : order.getProducts()) {
				total = total.add(p.getPrice());
			}
			model.addAttribute("total", total);
			model.addAttribute("order", order);
			model.addAttribute("orderproducts", checkInStockOrderDetails(order));
			navbarAttributes(model, principal);
		}
		
		navbarAttributes(model, principal);
		return "MyOrder";
	}

	// Obtenir les détails de ma commande

	@GetMapping("/me/order")
	public String getMyOrder(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}
		UserAccount user = userAccountRepository.findByUserName(principal.getName());

		Optional<Order> optOrder = orderRepository.findByUserAndOpenTrue(user);

		if (optOrder.isPresent()) {

			Order order = optOrder.get();
//			order.setNum("En attente de validation");
			
			BigDecimal total = BigDecimal.ZERO;

			for (Product p : order.getProducts()) {
				total = total.add(p.getPrice());
			}
			model.addAttribute("total", total);
			model.addAttribute("order", order);
			model.addAttribute("orderproducts", checkInStockOrderDetails(order));
		}
		navbarAttributes(model, principal);

		return "myOrder";
	}

	@RequestMapping(value = "/order/validate")
	public String validateOrder(Principal principal, Model model) {

		if (principal == null) {
			return "redirect:/login";
		}

		UserAccount user = userAccountRepository.findByUserName(principal.getName());
		Optional<Order> optOrder = orderRepository.findByUserAndOpenTrue(user);

		if (optOrder.isPresent()) {

			if (optOrder.get().getProducts().isEmpty()) {
				return "redirect:/products/list";
			}

			Order order = optOrder.get();
			order.setNum(order.createNum(order));
			setStock(order);
			order.setOpen(false);
			orderRepository.save(order);

			BigDecimal total = BigDecimal.ZERO;

			for (Product p : order.getProducts()) {
				total = total.add(p.getPrice());
			}
//			model.addAttribute("total", total);
//
//			model.addAttribute("order", order);
//			model.addAttribute("orderproducts", showOrderDetails(order));
			navbarAttributes(model, principal);
			return "redirect:/users/sendmail";
		}
		return "redirect:/products/list";
	}

	// Obtenir la liste de mes commandes

	@GetMapping("/me/orders")
	public String getMyOrders(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}
		
		List<Order> userOrders =  orderRepository.findByUserNotEmpty(userAccountRepository.findByUserName(principal.getName()));
        List<Order> closedOrders = userOrders.stream().filter(o -> o.isOpen() == false).collect(Collectors.toList());
		
		model.addAttribute("orders", closedOrders
				);
		navbarAttributes(model, principal);

		return "myOrders";
	}

	// Détails d'une commande
	@GetMapping("/me/order/details/{id}")
	public String getOrderDetails(@PathVariable("id") Long id, Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		Order order = orderService.getById(id);

		model.addAttribute("total", orderService.calculateTotalPrice(order));
		model.addAttribute("order", order);
		model.addAttribute("orderproducts", checkInStockOrderDetails(order));
		navbarAttributes(model, principal);

		return "orderDetails";
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//
//	private List<Product2> showOrderDetails(Order order) {
//
//		List<Product> prods = order.getProducts().stream().distinct().collect(Collectors.toList());
//		Product2 product2 = new Product2();
//		List<Product2> prods2 = new ArrayList<Product2>();
//
//		TypedQuery<OrdersProductsRs> tq = entityManager.createNamedQuery("getAllOrdersProductsQuery",
//				OrdersProductsRs.class);
//		List<OrdersProductsRs> ordersProducts = tq.getResultList();
//		List<OrdersProductsRs> productsInOrder = ordersProducts.stream().filter(op -> op.getOrderId() == order.getId())
//				.collect(Collectors.toList());
//
//		for (Product p : prods) {
//
//			product2.setDescription(p.getDescription());
//			product2.setId(p.getId());
//			product2.setName(p.getName());
//			product2.setPicture(p.getPicture());
//			product2.setPrice(p.getPrice());
//			product2.setStock(p.getStock());
//
//			long quantity = productsInOrder.stream().filter(pio -> pio.getProductId() == p.getId()).count();
//			product2.setQuantity(quantity);
//
//			if (product2.getStock() > product2.getQuantity()) {
//				product2.setCanBeAdd(true);
//			} else {
//				product2.setCanBeAdd(false);
//			}
//
//			product2.setTotalPrice(product2.calculateTotalPrice(product2.getPrice(), product2.getQuantity()));
//
//			prods2.add(product2);
//		}
//		prods2.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
//		return prods2;
//	}

//	private boolean IsInStock(Product product, Order order) {
//
//		Long productQuantity = order.getProducts().stream().filter(p -> p.getId() == product.getId()).count();
//
//		if (product.getStock() > productQuantity) {
//			return true;
//		}
//		return false;
//	}
	

	private void setStock(Order order) {

		List<Product> distinctProducts = order.getProducts().stream().distinct().collect(Collectors.toList());

		for (Product p : order.getProducts()) {
			distinctProducts.stream().filter(dp -> p.getId() == dp.getId()).forEach(dp2 -> {
				dp2.setSellNumber(dp2.getSellNumber() + 1);
				dp2.setStock(dp2.getStock() - 1);
//				productRepository.save(dp2);
			});
		}
		productRepository.saveAll(distinctProducts);

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

	private List<Product2> checkInStock() {

//		List<Product> prods = order.getProducts().stream().distinct().collect(Collectors.toList());
		List<Product2> prods2 = new ArrayList<Product2>();

		TypedQuery<OrdersProductsRs> tq = entityManager.createNamedQuery("getAllOrdersProductsQuery",
				OrdersProductsRs.class);
		List<OrdersProductsRs> ordersProducts = tq.getResultList();
//		List<OrdersProductsRs> productsInOrder = ordersProducts.stream().filter(op -> op.getOrderId() == order.getId())
//				.collect(Collectors.toList());

		for (Product p : productRepository.findAll()) {
			Product2 product2 = new Product2();

			product2.setDescription(p.getDescription());
			product2.setId(p.getId());
			product2.setName(p.getName());
			product2.setPicture(p.getPicture());
			product2.setPrice(p.getPrice());
			product2.setStock(p.getStock());

			long quantity = ordersProducts.stream().filter(pio -> pio.getProductId() == p.getId()).count();
			product2.setQuantity(quantity);

			if (product2.getStock() > product2.getQuantity()) {
				product2.setCanBeAdd(true);
			} else {
				product2.setCanBeAdd(false);
			}
			product2.setTotalPrice(product2.calculateTotalPrice(product2.getPrice(), product2.getQuantity()));

			prods2.add(product2);
			prods2.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
		}
		return prods2;
	}
	
	private List<Product2> checkInStockOrderDetails(Order order) {

		List<Product> prods = order.getProducts().stream().distinct().collect(Collectors.toList());
		List<Product2> prods2 = new ArrayList<Product2>();

//		TypedQuery<OrdersProductsRs> tq = entityManager.createNamedQuery("getAllOrdersProductsQuery",
//				OrdersProductsRs.class);
//		List<OrdersProductsRs> ordersProducts = tq.getResultList();
//		List<OrdersProductsRs> productsInOrder = ordersProducts.stream().filter(op -> op.getOrderId() == order.getId())
//				.collect(Collectors.toList());

		for (Product p : prods) {
			Product2 product2 = new Product2();

			product2.setDescription(p.getDescription());
			product2.setId(p.getId());
			product2.setName(p.getName());
			
			product2.setPicture(p.getPicture());
			product2.setPrice(p.getPrice());
			product2.setStock(p.getStock());

			long quantity = order.getProducts().stream().filter(pio -> pio.getId() == p.getId()).count();
			product2.setQuantity(quantity);

			if (product2.getStock() > product2.getQuantity()) {
				product2.setCanBeAdd(true);
			} else {
				product2.setCanBeAdd(false);
			}
			product2.setTotalPrice(product2.calculateTotalPrice(product2.getPrice(), product2.getQuantity()));

			prods2.add(product2);
			prods2.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
		}
		return prods2;
	}
	
	

}
