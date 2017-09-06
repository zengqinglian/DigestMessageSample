package eric.zeng.instinet.test.configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import eric.zeng.instinet.test.message.Message;

@Configuration
@ComponentScan(basePackages = { "eric.zeng.instinet.test" })
public class AppConfig {

	@Bean
	public BlockingQueue<Message> blockingQueue() {
		return new LinkedBlockingQueue<>();
	}

}
