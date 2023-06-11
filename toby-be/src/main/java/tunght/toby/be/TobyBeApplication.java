package tunght.toby.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"tunght.toby.be", "tunght.toby.common"})
@EntityScan(basePackages = {"tunght.toby.be", "tunght.toby.common"})
public class TobyBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TobyBeApplication.class, args);
    }

}
