package stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(
		  name = "PRODUCT_SEQ_GENERATOR",
		  sequenceName = "PRODUCT_SEQ",
		  initialValue = 1, allocationSize = 1)
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4730743031691276884L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ_GENERATOR")
	Long id;
	
	String name;
	
	BigDecimal price;
	
	String description;
	
	String picture;
	
	int stock;
	
	int sellNumber;
	
	boolean deleted;
	
//	@ManyToMany(mappedBy="products")
//	private List<Order> orders = new ArrayList<>();
	
	public Product(String name, BigDecimal price, String description, String picture, int stock, int sellNumber) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.picture = picture;
		this.stock = stock;
		this.sellNumber = sellNumber;
	}
	
}
