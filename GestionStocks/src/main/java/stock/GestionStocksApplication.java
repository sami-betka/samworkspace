package stock;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import stock.entity.AppRole;
import stock.entity.Product;
import stock.entity.UserAccount;
import stock.entity.UserRole;
import stock.repository.AppRoleRepository;
import stock.repository.ProductRepository;
import stock.repository.UserAccountRepository;
import stock.repository.UserRoleRepository;
import stock.utils.EncrytedPasswordUtils;

@SpringBootApplication
public class GestionStocksApplication {
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(GestionStocksApplication.class, args);

		AppRoleRepository appRoleRepository = ctx.getBean(AppRoleRepository.class);
		if (appRoleRepository.findAll().isEmpty()) {
			appRoleRepository.save(new AppRole(1l, "ROLE_ADMIN"));
			appRoleRepository.save(new AppRole(2l, "ROLE_USER"));
		}

		UserAccountRepository userAccountRepository = ctx.getBean(UserAccountRepository.class);
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);

		if (userAccountRepository.findByUserName("admin") == null) {
			UserAccount user = new UserAccount();
			user.setUserName("admin");
			user.setFirstName("Emilie");
			user.setLastName("Milou");
			user.setAdress("1000 avenue de Mon Coeur");
			user.setEmail("sami1206@hotmail.fr");
			user.setPhoneNumber("06 02 20 10 10");
			user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword("123"));
			UserRole userRole = new UserRole(user, appRoleRepository.findById(1L).get());

			userAccountRepository.save(user);
			userRoleRepository.save(userRole);
		}

		if (userAccountRepository.findByUserName("user") == null) {
			UserAccount user = new UserAccount();
			user.setUserName("user");
			user.setFirstName("Sami");
			user.setLastName("Betka");
			user.setAdress("11 villa des anges");
			user.setEmail("sami1206@hotmail.fr");
			user.setPhoneNumber("06 02 20 10 10");
			user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword("123"));
			UserRole userRole = new UserRole(user, appRoleRepository.findById(2L).get());

			userAccountRepository.save(user);
			userRoleRepository.save(userRole);
		}

		
		ProductRepository productRepository = ctx.getBean(ProductRepository.class);
		
		productRepository.save(new Product("Masque anti-virus", BigDecimal.valueOf(25.99),
				"Ce masque filtrant n'est autre qu'un bijou de technologie. "
						+ "Capable de filtrer à la fois les particules de pollution et les souches virales, "
						+ "il s'avèrera etre un element indispensable pour vous et vos proches.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDhU3rfRUOLDatM6g0BVqONTuv7ZDSrQil7f0Gzu9pEvYh1pgKmP0U_lHeGjqs44BSX34_4DI&usqp=CAc",
				56, 0));

		productRepository.save(new Product("Abri anti-zombie", BigDecimal.valueOf(94999.99),
				"Abri anti-zombie de dernière technologie. Peut contenir jusqu'à 30 personnes durant un mois.",
				"https://edito.seloger.com/sites/default/files/article/image/tiger_log_cabin.jpg", 5,1));

		productRepository.save(new Product("Désinfectant mains", BigDecimal.valueOf(29.99),
				"Un désinfectant puissant qui viendra à bout de tout vos microbes.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRFuVUcoFijOFtSwttTG2c2oCKujqSD9B6X86lq0wHxO_xROnn4lez92_HD0vgMsUq0MuInX7c&usqp=CAc",
				6,0));

		productRepository.save(new Product("Venin d'abeille", BigDecimal.valueOf(1999.99),
				"venin d'abeille de Wuhan. Une excellente alternative à un vaccin anti-coronavirus tandis que les recherches scientifiques battent de l'aile.",
				"/images/venin.jpg", 18,2));

		productRepository.save(new Product("Pieu anti-vampire", BigDecimal.valueOf(25.99),
				"Après de nombreuses attaques de vampires dans les rues de Paris, il est temps de prendre les devants avec ce pieu anti-vampire certifié par le Vatican.",
				"https://preview.free3d.com/img/2016/12/2162658835158795844/qbgz0ma7-900.jpg", 102,0));

		productRepository
				.save(new Product("50 masques jetables", BigDecimal.valueOf(569.99), "Lot de 50 masques jetables non réutilisables.  ",
						"https://images-eu.ssl-images-amazon.com/images/I/51x-nwTwlML.jpg", 12,3));

		productRepository.save(new Product("Papier hygiénique", BigDecimal.valueOf(58.99), "Papier hygiénique",
				"https://img.plusdebonsplans.com/2017/09/96-rouleaux-de-papier-toilette-mimosa.jpg", 40,0));

		productRepository.save(new Product("Pâtes (500kg)", BigDecimal.valueOf(399.99),
				"Demi-tonne de pâtes. Parce-que nourrir sa famille n'est pas une option.",
				"https://media.istockphoto.com/photos/three-packages-of-penne-pasta-picture-id470991406?k=6&m=470991406&s=612x612&w=0&h=Jj8yZS0A8M3a-fdfQiuPP5uTs9yv81xZ8vuNenfdn40=",
				103,4));

		productRepository.save(new Product("Kit de survie", BigDecimal.valueOf(149.99), "Survival kit",
				"https://contestimg.wish.com/api/webimage/5e0d94b90a5d0e0084d8b049-large.jpg?cache_buster=3c1faa49fae1fe5a3a7efea26173ef85",
				38,0));

		productRepository.save(new Product("Lance-pierre", BigDecimal.valueOf(59.99), "Lance-pierre artisanal",
				"https://denez.com/wp-content/uploads/2015/03/Lance-Pierre.jpg", 38,5));
		
		
		
		
		productRepository.save(new Product("Masque anti-virus", BigDecimal.valueOf(25.99),
				"Ce masque filtrant n'est autre qu'un bijou de technologie. "
						+ "Capable de filtrer à la fois les particules de pollution et les souches virales, "
						+ "il s'avèrera etre un element indispensable pour vous et vos proches.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDhU3rfRUOLDatM6g0BVqONTuv7ZDSrQil7f0Gzu9pEvYh1pgKmP0U_lHeGjqs44BSX34_4DI&usqp=CAc",
				56, 0));

		productRepository.save(new Product("Abri anti-zombie", BigDecimal.valueOf(94999.99),
				"Abri anti-zombie de dernière technologie. Peut contenir jusqu'à 30 personnes durant un mois.",
				"https://edito.seloger.com/sites/default/files/article/image/tiger_log_cabin.jpg", 5,1));

		productRepository.save(new Product("Désinfectant mains", BigDecimal.valueOf(29.99),
				"Un désinfectant puissant qui viendra à bout de tout vos microbes.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRFuVUcoFijOFtSwttTG2c2oCKujqSD9B6X86lq0wHxO_xROnn4lez92_HD0vgMsUq0MuInX7c&usqp=CAc",
				6,0));

		productRepository.save(new Product("Venin d'abeille", BigDecimal.valueOf(1999.99),
				"venin d'abeille de Wuhan. Une excellente alternative à un vaccin anti-coronavirus tandis que les recherches scientifiques battent de l'aile.",
				"/images/venin.jpg", 18,2));

		productRepository.save(new Product("Pieu anti-vampire", BigDecimal.valueOf(25.99),
				"Après de nombreuses attaques de vampires dans les rues de Paris, il est temps de prendre les devants avec ce pieu anti-vampire certifié par le Vatican.",
				"https://preview.free3d.com/img/2016/12/2162658835158795844/qbgz0ma7-900.jpg", 102,0));

		productRepository
				.save(new Product("50 masques jetables", BigDecimal.valueOf(569.99), "Lot de 50 masques jetables non réutilisables.  ",
						"https://images-eu.ssl-images-amazon.com/images/I/51x-nwTwlML.jpg", 12,3));

		productRepository.save(new Product("Papier hygiénique", BigDecimal.valueOf(58.99), "Papier hygiénique",
				"https://img.plusdebonsplans.com/2017/09/96-rouleaux-de-papier-toilette-mimosa.jpg", 40,0));

		productRepository.save(new Product("Pâtes (500kg)", BigDecimal.valueOf(399.99),
				"Demi-tonne de pâtes. Parce-que nourrir sa famille n'est pas une option.",
				"https://media.istockphoto.com/photos/three-packages-of-penne-pasta-picture-id470991406?k=6&m=470991406&s=612x612&w=0&h=Jj8yZS0A8M3a-fdfQiuPP5uTs9yv81xZ8vuNenfdn40=",
				103,4));

		productRepository.save(new Product("Kit de survie", BigDecimal.valueOf(149.99), "Survival kit",
				"https://contestimg.wish.com/api/webimage/5e0d94b90a5d0e0084d8b049-large.jpg?cache_buster=3c1faa49fae1fe5a3a7efea26173ef85",
				38,0));

		productRepository.save(new Product("Lance-pierre", BigDecimal.valueOf(59.99), "Lance-pierre artisanal",
				"https://denez.com/wp-content/uploads/2015/03/Lance-Pierre.jpg", 38,5));
		
		
		
		
		
		
		
		productRepository.save(new Product("Masque anti-virus", BigDecimal.valueOf(25.99),
				"Ce masque filtrant n'est autre qu'un bijou de technologie. "
						+ "Capable de filtrer à la fois les particules de pollution et les souches virales, "
						+ "il s'avèrera etre un element indispensable pour vous et vos proches.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDhU3rfRUOLDatM6g0BVqONTuv7ZDSrQil7f0Gzu9pEvYh1pgKmP0U_lHeGjqs44BSX34_4DI&usqp=CAc",
				56, 0));

		productRepository.save(new Product("Abri anti-zombie", BigDecimal.valueOf(94999.99),
				"Abri anti-zombie de dernière technologie. Peut contenir jusqu'à 30 personnes durant un mois.",
				"https://edito.seloger.com/sites/default/files/article/image/tiger_log_cabin.jpg", 5,1));

		productRepository.save(new Product("Désinfectant mains", BigDecimal.valueOf(29.99),
				"Un désinfectant puissant qui viendra à bout de tout vos microbes.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRFuVUcoFijOFtSwttTG2c2oCKujqSD9B6X86lq0wHxO_xROnn4lez92_HD0vgMsUq0MuInX7c&usqp=CAc",
				6,0));

		productRepository.save(new Product("Venin d'abeille", BigDecimal.valueOf(1999.99),
				"venin d'abeille de Wuhan. Une excellente alternative à un vaccin anti-coronavirus tandis que les recherches scientifiques battent de l'aile.",
				"/images/venin.jpg", 18,2));

		productRepository.save(new Product("Pieu anti-vampire", BigDecimal.valueOf(25.99),
				"Après de nombreuses attaques de vampires dans les rues de Paris, il est temps de prendre les devants avec ce pieu anti-vampire certifié par le Vatican.",
				"https://preview.free3d.com/img/2016/12/2162658835158795844/qbgz0ma7-900.jpg", 102,0));

		productRepository
				.save(new Product("50 masques jetables", BigDecimal.valueOf(569.99), "Lot de 50 masques jetables non réutilisables.  ",
						"https://images-eu.ssl-images-amazon.com/images/I/51x-nwTwlML.jpg", 12,3));

		productRepository.save(new Product("Papier hygiénique", BigDecimal.valueOf(58.99), "Papier hygiénique",
				"https://img.plusdebonsplans.com/2017/09/96-rouleaux-de-papier-toilette-mimosa.jpg", 40,0));

		productRepository.save(new Product("Pâtes (500kg)", BigDecimal.valueOf(399.99),
				"Demi-tonne de pâtes. Parce-que nourrir sa famille n'est pas une option.",
				"https://media.istockphoto.com/photos/three-packages-of-penne-pasta-picture-id470991406?k=6&m=470991406&s=612x612&w=0&h=Jj8yZS0A8M3a-fdfQiuPP5uTs9yv81xZ8vuNenfdn40=",
				103,4));

		productRepository.save(new Product("Kit de survie", BigDecimal.valueOf(149.99), "Survival kit",
				"https://contestimg.wish.com/api/webimage/5e0d94b90a5d0e0084d8b049-large.jpg?cache_buster=3c1faa49fae1fe5a3a7efea26173ef85",
				38,0));

		productRepository.save(new Product("Lance-pierre", BigDecimal.valueOf(59.99), "Lance-pierre artisanal",
				"https://denez.com/wp-content/uploads/2015/03/Lance-Pierre.jpg", 38,5));
		
		
		
		
		
		
		
		productRepository.save(new Product("Masque anti-virus", BigDecimal.valueOf(25.99),
				"Ce masque filtrant n'est autre qu'un bijou de technologie. "
						+ "Capable de filtrer à la fois les particules de pollution et les souches virales, "
						+ "il s'avèrera etre un element indispensable pour vous et vos proches.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDhU3rfRUOLDatM6g0BVqONTuv7ZDSrQil7f0Gzu9pEvYh1pgKmP0U_lHeGjqs44BSX34_4DI&usqp=CAc",
				56, 0));

		productRepository.save(new Product("Abri anti-zombie", BigDecimal.valueOf(94999.99),
				"Abri anti-zombie de dernière technologie. Peut contenir jusqu'à 30 personnes durant un mois.",
				"https://edito.seloger.com/sites/default/files/article/image/tiger_log_cabin.jpg", 5,1));

		productRepository.save(new Product("Désinfectant mains", BigDecimal.valueOf(29.99),
				"Un désinfectant puissant qui viendra à bout de tout vos microbes.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRFuVUcoFijOFtSwttTG2c2oCKujqSD9B6X86lq0wHxO_xROnn4lez92_HD0vgMsUq0MuInX7c&usqp=CAc",
				6,0));

		productRepository.save(new Product("Venin d'abeille", BigDecimal.valueOf(1999.99),
				"venin d'abeille de Wuhan. Une excellente alternative à un vaccin anti-coronavirus tandis que les recherches scientifiques battent de l'aile.",
				"/images/venin.jpg", 18,2));

		productRepository.save(new Product("Pieu anti-vampire", BigDecimal.valueOf(25.99),
				"Après de nombreuses attaques de vampires dans les rues de Paris, il est temps de prendre les devants avec ce pieu anti-vampire certifié par le Vatican.",
				"https://preview.free3d.com/img/2016/12/2162658835158795844/qbgz0ma7-900.jpg", 102,0));

		productRepository
				.save(new Product("50 masques jetables", BigDecimal.valueOf(569.99), "Lot de 50 masques jetables non réutilisables.  ",
						"https://images-eu.ssl-images-amazon.com/images/I/51x-nwTwlML.jpg", 12,3));

		productRepository.save(new Product("Papier hygiénique", BigDecimal.valueOf(58.99), "Papier hygiénique",
				"https://img.plusdebonsplans.com/2017/09/96-rouleaux-de-papier-toilette-mimosa.jpg", 40,0));

		productRepository.save(new Product("Pâtes (500kg)", BigDecimal.valueOf(399.99),
				"Demi-tonne de pâtes. Parce-que nourrir sa famille n'est pas une option.",
				"https://media.istockphoto.com/photos/three-packages-of-penne-pasta-picture-id470991406?k=6&m=470991406&s=612x612&w=0&h=Jj8yZS0A8M3a-fdfQiuPP5uTs9yv81xZ8vuNenfdn40=",
				103,4));

		productRepository.save(new Product("Kit de survie", BigDecimal.valueOf(149.99), "Survival kit",
				"https://contestimg.wish.com/api/webimage/5e0d94b90a5d0e0084d8b049-large.jpg?cache_buster=3c1faa49fae1fe5a3a7efea26173ef85",
				38,0));

		productRepository.save(new Product("Lance-pierre", BigDecimal.valueOf(59.99), "Lance-pierre artisanal",
				"https://denez.com/wp-content/uploads/2015/03/Lance-Pierre.jpg", 38,5));
		
		
		
		
		
		
		
		productRepository.save(new Product("Masque anti-virus", BigDecimal.valueOf(25.99),
				"Ce masque filtrant n'est autre qu'un bijou de technologie. "
						+ "Capable de filtrer à la fois les particules de pollution et les souches virales, "
						+ "il s'avèrera etre un element indispensable pour vous et vos proches.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDhU3rfRUOLDatM6g0BVqONTuv7ZDSrQil7f0Gzu9pEvYh1pgKmP0U_lHeGjqs44BSX34_4DI&usqp=CAc",
				56, 0));

		productRepository.save(new Product("Abri anti-zombie", BigDecimal.valueOf(94999.99),
				"Abri anti-zombie de dernière technologie. Peut contenir jusqu'à 30 personnes durant un mois.",
				"https://edito.seloger.com/sites/default/files/article/image/tiger_log_cabin.jpg", 5,1));

		productRepository.save(new Product("Désinfectant mains", BigDecimal.valueOf(29.99),
				"Un désinfectant puissant qui viendra à bout de tout vos microbes.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRFuVUcoFijOFtSwttTG2c2oCKujqSD9B6X86lq0wHxO_xROnn4lez92_HD0vgMsUq0MuInX7c&usqp=CAc",
				6,0));

		productRepository.save(new Product("Venin d'abeille", BigDecimal.valueOf(1999.99),
				"venin d'abeille de Wuhan. Une excellente alternative à un vaccin anti-coronavirus tandis que les recherches scientifiques battent de l'aile.",
				"/images/venin.jpg", 18,2));

		productRepository.save(new Product("Pieu anti-vampire", BigDecimal.valueOf(25.99),
				"Après de nombreuses attaques de vampires dans les rues de Paris, il est temps de prendre les devants avec ce pieu anti-vampire certifié par le Vatican.",
				"https://preview.free3d.com/img/2016/12/2162658835158795844/qbgz0ma7-900.jpg", 102,0));

		productRepository
				.save(new Product("50 masques jetables", BigDecimal.valueOf(569.99), "Lot de 50 masques jetables non réutilisables.  ",
						"https://images-eu.ssl-images-amazon.com/images/I/51x-nwTwlML.jpg", 12,3));

		productRepository.save(new Product("Papier hygiénique", BigDecimal.valueOf(58.99), "Papier hygiénique",
				"https://img.plusdebonsplans.com/2017/09/96-rouleaux-de-papier-toilette-mimosa.jpg", 40,0));

		productRepository.save(new Product("Pâtes (500kg)", BigDecimal.valueOf(399.99),
				"Demi-tonne de pâtes. Parce-que nourrir sa famille n'est pas une option.",
				"https://media.istockphoto.com/photos/three-packages-of-penne-pasta-picture-id470991406?k=6&m=470991406&s=612x612&w=0&h=Jj8yZS0A8M3a-fdfQiuPP5uTs9yv81xZ8vuNenfdn40=",
				103,4));

		productRepository.save(new Product("Kit de survie", BigDecimal.valueOf(149.99), "Survival kit",
				"https://contestimg.wish.com/api/webimage/5e0d94b90a5d0e0084d8b049-large.jpg?cache_buster=3c1faa49fae1fe5a3a7efea26173ef85",
				38,0));

		productRepository.save(new Product("Lance-pierre", BigDecimal.valueOf(59.99), "Lance-pierre artisanal",
				"https://denez.com/wp-content/uploads/2015/03/Lance-Pierre.jpg", 38,5));
		
		
		
		
		// Serialization

		List<Product> prods = new ArrayList<>();
		Product p1 = new Product("Masque anti-virus"
				, BigDecimal.valueOf(59.99)				, "Ce masque filtrant n'est autre qu'un bijou de technologie. "
						+ "Capable de filtrer à la fois les particules de pollution et les souches virales, "
						+ "il s'avèrera etre un element indispensable pour vous et vos proches."
				,"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDhU3rfRUOLDatM6g0BVqONTuv7ZDSrQil7f0Gzu9pEvYh1pgKmP0U_lHeGjqs44BSX34_4DI&usqp=CAc"
				,56);
		
		Product p2 = new Product("Abri anti-zombie"
				, BigDecimal.valueOf(59.99)				, "Abri anti-zombie de dernière technologie. Peut contenir jusqu'à 30 personnes durant un mois."
				,"https://edito.seloger.com/sites/default/files/article/image/tiger_log_cabin.jpg"
				, 5);
		
		Product p3 =new Product("Désinfectant mains"
				, BigDecimal.valueOf(59.99)				, "Un désinfectant puissant qui viendra à bout de tout vos microbes."
				,"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRFuVUcoFijOFtSwttTG2c2oCKujqSD9B6X86lq0wHxO_xROnn4lez92_HD0vgMsUq0MuInX7c&usqp=CAc"
				,6);
		
		Product p4 = new Product("Venin d'abeille"
				, BigDecimal.valueOf(59.99)				, "venin d'abeille de Wuhan. Une excellente alternative à un vaccin anti-coronavirus tandis que les recherches scientifiques battent de l'aile."
				,"/images/venin.jpg"
				,18);
		
		Product p5 = new Product("Pieu anti-vampire"
				, BigDecimal.valueOf(59.99)				, "Après de nombreuses attaques de vampires dans les rues de Paris, il est temps de prendre les devants avec ce pieu anti-vampire certifié par le Vatican."
				,"https://preview.free3d.com/img/2016/12/2162658835158795844/qbgz0ma7-900.jpg"
				, 102);
		
		Product p6 = new Product("50 masques jetables"
				, BigDecimal.valueOf(59.99)				, "Lot de 50 masques jetables non réutilisables.  "
				,"https://images-eu.ssl-images-amazon.com/images/I/51x-nwTwlML.jpg"
				, 12);
		
		Product p7 = new Product("Papier hygiénique"
				, BigDecimal.valueOf(59.99)				, "Papier hygiénique"
				,"https://img.plusdebonsplans.com/2017/09/96-rouleaux-de-papier-toilette-mimosa.jpg"
				, 40);
		
		Product p8 = new Product("Pâtes (500kg)"
				, BigDecimal.valueOf(59.99)				, "Demi-tonne de pâtes. Parce-que nourrir sa famille n'est pas une option."
				,"https://media.istockphoto.com/photos/three-packages-of-penne-pasta-picture-id470991406?k=6&m=470991406&s=612x612&w=0&h=Jj8yZS0A8M3a-fdfQiuPP5uTs9yv81xZ8vuNenfdn40="
				,103);
		
		Product p9 = new Product("Kit de survie"
				, BigDecimal.valueOf(59.99)				, "Survival kit"
				,"https://contestimg.wish.com/api/webimage/5e0d94b90a5d0e0084d8b049-large.jpg?cache_buster=3c1faa49fae1fe5a3a7efea26173ef85"
				,38);
		
		Product p10 = new Product("Lance-pierre"
				, BigDecimal.valueOf(59.99)				, "Lance-pierre artisanal"
				,"https://denez.com/wp-content/uploads/2015/03/Lance-Pierre.jpg"
				,38);
		
		
		
		Long id = 1l;
		
		p1.setId(id);
		id ++;
		prods.add(p1);
		
		p2.setId(id);
		id ++;
		prods.add(p2);
		
		p3.setId(id);
		id ++;
		prods.add(p3);

		p4.setId(id);
		id ++;
		prods.add(p4);

		p5.setId(id);
		id ++;
		prods.add(p5);

		p6.setId(id);
		id ++;
		prods.add(p6);

		p7.setId(id);
		id ++;
		prods.add(p7);

		p8.setId(id);
		id ++;
		prods.add(p8);

		p9.setId(id);
		id ++;
		prods.add(p9);

		p10.setId(id);
		id ++;
		prods.add(p10);
		

		try {
			FileOutputStream fos = new FileOutputStream("productlist.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(prods);
			oos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
