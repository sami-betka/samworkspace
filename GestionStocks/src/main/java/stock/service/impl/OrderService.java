package stock.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import stock.entity.Order;
import stock.entity.Product;
import stock.entity.UserAccount;
import stock.repository.OrderRepository;
import stock.repository.ProductRepository;
import stock.repository.UserAccountRepository;

@Data
@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserAccountRepository userAccountRepository;

	public Order getById(Long id) {
		return orderRepository.findById(id).get();
	}
	
	public Optional<Order> getByUserAndOpenTrue (UserAccount user){
		return orderRepository.findByUserAndOpenTrue(user);
		}
	
	public void addProductToOrder(Long id, UserAccount user) {
		
		Product product = productRepository.findById(id).get();
		Optional<Order> optOrder = orderRepository.findByUserAndOpenTrue(user);

		if (optOrder.isPresent()) {

			Order order = optOrder.get();

			user.getOrders().remove(order);
			order.getProducts().add(product);
			user.getOrders().add(order);

			orderRepository.save(order);
			userAccountRepository.save(user);
//		    model.addAttribute("isinstock", IsInStock(product, order));		

		} else {

			Order order = new Order();
			order.setDate(LocalDate.now());
			order.setUser(user);
			order.setOpen(true);
			order.setNum("En attente de validation");

			order.getProducts().add(product);
			user.getOrders().add(order);

			orderRepository.save(order);
			userAccountRepository.save(user);
		}
	}

	public void addProductToMyOrder2 (UserAccount user, Order order, Product product) {
		user.getOrders().remove(order);
		order.getProducts().add(product);
		user.getOrders().add(order);
		order.setNum("En attente de validation");

		orderRepository.save(order);
		userAccountRepository.save(user);
	}
	//////////////////////////////////////////////////////////////

	public BigDecimal calculateTotalPrice(Order order) {

		BigDecimal total = BigDecimal.ZERO;
		for (Product p : order.getProducts()) {
			total = total.add(p.getPrice());
		}
		return total;
	}

}
