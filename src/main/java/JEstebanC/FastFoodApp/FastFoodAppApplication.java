package JEstebanC.FastFoodApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import JEstebanC.FastFoodApp.service.CloudinaryService;

@SpringBootApplication()
public class FastFoodAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastFoodAppApplication.class, args);
	}
	
//	set the encryptor 
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CloudinaryService cloudinaryService() {
		return new CloudinaryService();
	}
}
