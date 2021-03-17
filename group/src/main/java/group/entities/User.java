package group.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users") 

public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	private String prenom;
	private int age;
	private String mail;
	
	

}
