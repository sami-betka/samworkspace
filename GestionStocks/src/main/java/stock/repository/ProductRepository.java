package stock.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import stock.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query(nativeQuery = true, 
			value = "SELECT COUNT(products_id)from orders_products "
					+ "WHERE products_id = :productId "
					+ "AND order_id = :orderId")
	int findProductQuantity(Long orderId, Long productId);
	
	List<Product> findByDeletedFalse();	
	
	@Query("select p from Product p where p.deleted = false")
	public Page<Product>findProducts(Pageable pageable);
	
	public List <Product> findTop5ByOrderBySellNumberDesc();

	
}
