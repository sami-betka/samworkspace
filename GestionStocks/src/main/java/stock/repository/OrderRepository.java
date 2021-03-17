package stock.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import stock.entity.Order;
import stock.entity.UserAccount;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Optional<Order> findByUserAndOpenTrue(UserAccount user);
	
	List<Order> findByOpenFalse();

	@Query("SELECT o FROM Order o "
			+ "WHERE o.user = :user "
			+ "AND o.products IS NOT EMPTY")
	List<Order> findByUserNotEmpty(UserAccount user);
	
	Order findByNum (String num);
	
//	@Query("SELECT "
//			+ "FROM Order o "
//			+ "LEFT JOIN ")
//	List<Map<String, Object>> createOrdersProducts ();


}
