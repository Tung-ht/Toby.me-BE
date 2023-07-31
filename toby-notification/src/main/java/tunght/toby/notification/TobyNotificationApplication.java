package tunght.toby.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"tunght.toby.notification", "tunght.toby.common"})
@EntityScan(basePackages = {"tunght.toby.notification", "tunght.toby.common"})
public class TobyNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TobyNotificationApplication.class, args);
	}

}
