package com.mt.ecommerce.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** * Main application class for the Product service.
 */
@SpringBootApplication
public class ProductApplication {

	/** * The entry point of the Product application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}
