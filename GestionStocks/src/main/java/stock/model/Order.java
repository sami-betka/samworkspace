package stock.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import lombok.Data;
import stock.repository.resultset.OrdersProductsRs;

@Data
@Entity
@Table(name = "orders")
@SequenceGenerator(
		  name = "ORDER_SEQ_GENERATOR",
		  sequenceName = "ORDER_SEQ",
		  initialValue = 1, allocationSize = 1)
@SqlResultSetMapping(name = "OrdersProductsRsMapping", classes = {
		@ConstructorResult(targetClass = OrdersProductsRs.class, columns = {
				@ColumnResult(name = "orderId", type = Long.class),
				@ColumnResult(name = "productId", type = Long.class) }) })

@NamedNativeQuery(name = "getAllOrdersProductsQuery", resultSetMapping = "OrdersProductsRsMapping", resultClass = OrdersProductsRs.class, query = "SELECT op.order_id AS orderId, op.products_id AS productId "
		+ "FROM orders o " + "LEFT JOIN orders_products op ON o.id = op.order_id")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQ_GENERATOR")
	private Long id;

	private LocalDate date;

	private String num;

	@ManyToOne()
	private UserAccount user;

//	@ManyToMany
	@ManyToMany
	private List<Product> products = new ArrayList<>();

	private boolean open;

	public String createNum( Order order ) {
		
		String orderNum = order.getId() + "-" +  LocalTime.now().atDate(LocalDate.now()).toString() + order.getUser().getUserId();
		return orderNum;
	}
}
