package eric.zeng.instinet.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "eric.zeng.instinet.test" })
public class Application {

	public Application() {
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
}
