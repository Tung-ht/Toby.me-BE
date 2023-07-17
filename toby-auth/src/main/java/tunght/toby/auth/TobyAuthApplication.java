package tunght.toby.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"tunght.toby.auth", "tunght.toby.common"})
@EntityScan(basePackages = {"tunght.toby.auth", "tunght.toby.common"})
public class TobyAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(TobyAuthApplication.class, args);
	}

}
