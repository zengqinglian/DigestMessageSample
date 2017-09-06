package eric.zeng.instinet.test.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eric.zeng.instinet.test.consumer.MessageConsumer;
import eric.zeng.instinet.test.producer.DummyMessageProducer;

@Component
public class ProducerConsumerThreadStarter {

	@Autowired
	public ProducerConsumerThreadStarter(MessageConsumer consumer, DummyMessageProducer producer) {
		Thread t1 = new Thread(consumer);
		Thread t2 = new Thread(producer);

		t1.start();
		t2.start();
	}

}
