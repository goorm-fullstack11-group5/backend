package goorm.fullstack.webide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WebideApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebideApplication.class, args);
	}

}
