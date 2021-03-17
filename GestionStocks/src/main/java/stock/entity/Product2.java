package stock.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product2 {
	
	Long id;
	
	String name;
	
	BigDecimal price;
	
	String description;
	
	String picture;
	
	int stock;
	
	long quantity;
	
	BigDecimal totalPrice;
	
	boolean canBeAdd;
	
	
	
	public BigDecimal calculateTotalPrice(BigDecimal price, long quantity) {
		
		BigDecimal totalPrice = price.multiply(new BigDecimal(quantity)).setScale(2);
		
		return totalPrice;
		}
	
	
}
