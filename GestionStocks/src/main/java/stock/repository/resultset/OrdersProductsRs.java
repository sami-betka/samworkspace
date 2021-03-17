package stock.repository.resultset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersProductsRs {

	public static final String ORDER_ID = "orderId";
	public static final String PRODUCT_ID = "productId";
	
	private Long orderId;
	private Long productId;
	
	
}
