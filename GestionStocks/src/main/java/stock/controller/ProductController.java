package stock.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import stock.entity.Order;
import stock.entity.Product;
import stock.entity.Product2;
import stock.entity.UserAccount;
import stock.repository.OrderRepository;
import stock.repository.ProductRepository;
import stock.repository.UserAccountRepository;
import stock.repository.resultset.OrdersProductsRs;
import stock.service.impl.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	
//	DecimalFormat df = new DecimalFormat("0.00");

	@Autowired
	EntityManager entityManager;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductService productService;

//	@GetMapping(value = "/list")
//	public String getProducts(Model model, Principal principal) {
//		
//		
//		model.addAttribute("products", checkInStock(principal));
//		model.addAttribute("order", productRepository.findAll());
//		navbarAttributes(model, principal);
//		
////		return "test2";
//		return"test";
////		return "products";
////		return "cardproject";
//		
//	}

	// Obtenir les détails d'un produit
	@GetMapping("/details/{id}")
	public String getProduct(@PathVariable("id") Long id, Model model, Principal principal) {

		Optional<Product> optProduct = productRepository.findById(id);


		if (optProduct.isPresent()) {
			Product product = optProduct.get();
//			model.addAttribute("product", productService.mapToProduct2(product, principal));
			model.addAttribute("product", getProductStock(principal, product));

			navbarAttributes(model, principal);
			return "productDetails";
		}
		return "redirect:/products/list";
	}

	// Obtenir la liste des produits
	@GetMapping("/list")
	public String getProductList(Model model, Principal principal,
			@RequestParam(name = "page", defaultValue = "0") int p
//			,@RequestParam(name = "msg", defaultValue = "", required = false) String msg
//			,
//			@RequestParam(name="motCle", defaultValue = "")String mc
//			,@RequestParam(name="category", defaultValue = "")Category category
			) {
//		UserAccount user = userAccountRepository.findByUserName(principal.getName())

		List<Product2> products = getStock(principal);

		int pageSize = 15;
		Pageable pageable = PageRequest.of(p, pageSize);
		if(p * pageSize > products.size()) {
			return "redirect:/products/list";
		}
		
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > products.size() ? products.size()
				: (start + pageable.getPageSize());
		Page<Product2> pageProducts = new PageImpl<Product2>(products.subList(start, end), pageable, products.size());
		

//		Page<Product>pageProducts=productRepository.findAll(PageRequest.of(p, 5));
//		Page<Product>pageProducts=productRepository.findProducts("%"+mc+"%",PageRequest.of(p, 12));
//		Page<Product>pageProducts=productRepository.findProductsByCategory(category ,"%"+mc+"%",PageRequest.of(p, 12));

		int pagesCount = pageProducts.getTotalPages();
		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("pages", pages);
		model.addAttribute("pageProducts", pageProducts);
		model.addAttribute("currentpage", p);
//		model.addAttribute("motCle", mc);

		navbarAttributes(model, principal);
//		model.addAttribute("pageProductsTop5", products.subList(0, 5));
		model.addAttribute("pageProductsTop5", 
		 productService.mapListToProduct2(productRepository.findTop5ByOrderBySellNumberDesc(), principal));
//        model.addAttribute("msg", msg);
//		return "test";
		return "test2";
	}
	
//	@GetMapping("/top10")
//	public String getTop4 (Model model, Principal principal) {
//		
//		model.addAttribute("pageProductsTop4", pageProductsTop4);
//		return null;
//		}

	@GetMapping(value = "/add-product")
	public String addProduct(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}

		model.addAttribute("product", new Product());
		navbarAttributes(model, principal);
		return "addProduct";

	}

	@PostMapping(value = "/save-product")
	public String saveProduct(@Valid Product product, BindingResult bindingresult)
			throws IllegalStateException, IOException {

		if (bindingresult.hasErrors()) {
			return "redirect:/products/add-product";
		}

//		if(!(file.isEmpty())) {
//			product.setPhoto(file.getOriginalFilename());
//			file.transferTo(new File("C:\\Users\\s.betka\\Desktop\\sco\\"+file.getOriginalFilename()));
//		}
		productRepository.save(product);
		return "redirect:/products/list";

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	
	private Product2 getProductStock(Principal principal, Product p) {

//		System.out.println(principal.getName() + "hhh");
		Order order;

		if (principal != null) {

			Optional<Order> optOrder = orderRepository
					.findByUserAndOpenTrue(userAccountRepository.findByUserName(principal.getName()));

			if (optOrder.isPresent()) {
				order = optOrder.get();
			} else {
				order = new Order();
			}
		} else {

			order = new Order();
		}

//		List<Product> prods = order.getProducts().stream().distinct().collect(Collectors.toList());

		TypedQuery<OrdersProductsRs> tq = entityManager.createNamedQuery("getAllOrdersProductsQuery",
				OrdersProductsRs.class);
		
		List<OrdersProductsRs> ordersProducts = tq.getResultList();	
		
		Set<Long> closedOrdersIds = orderRepository.findAll().stream()
				.filter(o -> o.isOpen() == true)
				.map(Order :: getId)
				.collect(Collectors.toSet());
		
		List<OrdersProductsRs> productsInClosedOrders = ordersProducts.stream()
				.filter(op -> closedOrdersIds.contains(op.getOrderId()) || op.getOrderId() == order.getId())
				.collect(Collectors.toList());
		
		
		

			Product2 product2 = new Product2();

			product2.setDescription(p.getDescription());
			product2.setId(p.getId());
			product2.setName(p.getName());
			product2.setPicture(p.getPicture());
			product2.setPrice(p.getPrice());
			product2.setStock(p.getStock());

			long quantity = productsInClosedOrders.stream().filter(op -> op.getProductId() == p.getId()).count();
			product2.setQuantity(quantity);

			if (product2.getStock() > product2.getQuantity()) {
				product2.setCanBeAdd(true);
			} else {
				product2.setCanBeAdd(false);
			}
			product2.setTotalPrice(product2.calculateTotalPrice(product2.getPrice(), product2.getQuantity()));

		return product2;
	}
	

	private List<Product2> getStock(Principal principal) {

//		System.out.println(principal.getName() + "hhh");
		Order order;

		if (principal != null) {

			Optional<Order> optOrder = orderRepository
					.findByUserAndOpenTrue(userAccountRepository.findByUserName(principal.getName()));

			if (optOrder.isPresent()) {
				order = optOrder.get();
			} else {
				order = new Order();
			}
		} else {

			order = new Order();
		}

//		List<Product> prods = order.getProducts().stream().distinct().collect(Collectors.toList());
		List<Product2> prods2 = new ArrayList<Product2>();

		TypedQuery<OrdersProductsRs> tq = entityManager.createNamedQuery("getAllOrdersProductsQuery",
				OrdersProductsRs.class);
		
		List<OrdersProductsRs> ordersProducts = tq.getResultList();	
		
		Set<Long> closedOrdersIds = orderRepository.findAll().stream()
				.filter(o -> o.isOpen() == true)
				.map(Order :: getId)
				.collect(Collectors.toSet());
		
		List<OrdersProductsRs> productsInClosedOrders = ordersProducts.stream()
				.filter(op -> closedOrdersIds.contains(op.getOrderId()) || op.getOrderId() == order.getId())
				.collect(Collectors.toList());
		
		
		

		for (Product p : productRepository.findAll()) {
			Product2 product2 = new Product2();

			product2.setDescription(p.getDescription());
			product2.setId(p.getId());
			product2.setName(p.getName());
			product2.setPicture(p.getPicture());
			product2.setPrice(p.getPrice());
			product2.setStock(p.getStock());

			long quantity = productsInClosedOrders.stream().filter(op -> op.getProductId() == p.getId()).count();
			product2.setQuantity(quantity);

			if (product2.getStock() > product2.getQuantity()) {
				product2.setCanBeAdd(true);
			} else {
				product2.setCanBeAdd(false);
			}
			product2.setTotalPrice(product2.calculateTotalPrice(product2.getPrice(), product2.getQuantity()));

			prods2.add(product2);
		}
		prods2.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
		return prods2;
	}
	
	private List<Product2> getStockSerializable(Principal principal) {

//		System.out.println(principal.getName() + "hhh");
		Order order;

		if (principal != null) {

			Optional<Order> optOrder = orderRepository
					.findByUserAndOpenTrue(userAccountRepository.findByUserName(principal.getName()));

			if (optOrder.isPresent()) {
				order = optOrder.get();
			} else {
				order = new Order();
			}
		} else {

			order = new Order();
		}

//		List<Product> prods = order.getProducts().stream().distinct().collect(Collectors.toList());
		List<Product2> prods2 = new ArrayList<Product2>();

		TypedQuery<OrdersProductsRs> tq = entityManager.createNamedQuery("getAllOrdersProductsQuery",
				OrdersProductsRs.class);
		List<OrdersProductsRs> ordersProducts = tq.getResultList();
		List<OrdersProductsRs> productsInOrder = ordersProducts.stream().filter(op -> op.getOrderId() == order.getId())
				.collect(Collectors.toList());
		
		try {
			FileInputStream fis = new FileInputStream("productlist.txt");
			ObjectInputStream ois =new ObjectInputStream(fis);
			List<Product> products = (List<Product>) ois.readObject();
			
	

		for (Product p : products) {
			Product2 product2 = new Product2();

			product2.setDescription(p.getDescription());
			product2.setId(p.getId());
			product2.setName(p.getName());
			product2.setPicture(p.getPicture());
			product2.setPrice(p.getPrice());
			product2.setStock(p.getStock());

			long quantity = productsInOrder.stream().filter(op -> op.getProductId() == p.getId()).count();
			product2.setQuantity(quantity);

			if (product2.getStock() > product2.getQuantity()) {
				product2.setCanBeAdd(true);
			} else {
				product2.setCanBeAdd(false);
			}
			product2.setTotalPrice(product2.calculateTotalPrice(product2.getPrice(), product2.getQuantity()));

			prods2.add(product2);
		}
		prods2.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prods2;
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

//	private void navbarAttributes(Model model, Principal principal) {
//
//		if (principal != null) {
//
//			UserAccount user = userAccountRepository.findByUserName(principal.getName());
//			model.addAttribute("user", user);
//
//			Optional<Order> optOrder = orderRepository.findByUserAndOpenTrue(user);
//
//			if (optOrder.isPresent()) {
//
//				Order order = optOrder.get();
////				List<Long> productsIds = order.getProducts().stream().map(Product::getId).collect(Collectors.toList());
////				model.addAttribute("orderproductsids", productsIds);
//
//				if (order.getProducts().isEmpty()) {
//					model.addAttribute("quantity", 0);
//				} else {
//					model.addAttribute("quantity", order.getProducts().size());
//				}
//			} else {
//				model.addAttribute("quantity", 0);
//
//			}
//			if (user != null) {
//				model.addAttribute("ordersTrue", user.getOrders().stream().filter(o -> !o.isOpen()).count());
//			}
//		}
//	}

}
