package stock.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import stock.entity.Order;
import stock.entity.Product;
import stock.entity.Product2;
import stock.repository.OrderRepository;
import stock.repository.ProductRepository;
import stock.repository.UserAccountRepository;
import stock.repository.resultset.OrdersProductsRs;

@Data
@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	EntityManager entityManager;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	public Optional<Product> getById(Long id) {
		return productRepository.findById(id);
	}

	public Product2 mapToProduct2(Product p, Principal principal) {

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

		long quantity = productsInClosedOrders.stream().filter(pio -> pio.getProductId() == p.getId()).count();
		product2.setQuantity(quantity);

		if (product2.getStock() > product2.getQuantity()) {
			product2.setCanBeAdd(true);
		} else {
			product2.setCanBeAdd(false);
		}
	
		product2.setTotalPrice(product2.calculateTotalPrice(product2.getPrice(), product2.getQuantity()));

		return product2;

	}
	
	public List<Product2> mapListToProduct2(List<Product> products, Principal principal) {

		List<Product2> prods = new ArrayList<>();
		
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

		for(Product p: products) {
		
		Product2 product2 = new Product2();

		product2.setDescription(p.getDescription());
		product2.setId(p.getId());
		product2.setName(p.getName());
		product2.setPicture(p.getPicture());
		product2.setPrice(p.getPrice());
		product2.setStock(p.getStock());

		long quantity = productsInClosedOrders.stream().filter(pio -> pio.getProductId() == p.getId()).count();
		product2.setQuantity(quantity);

		if (product2.getStock() > product2.getQuantity()) {
			product2.setCanBeAdd(true);
		} else {
			product2.setCanBeAdd(false);
		}
		product2.setTotalPrice(product2.calculateTotalPrice(product2.getPrice(), product2.getQuantity()));
        
		prods.add(product2);
		}
		return prods;

	}

}
