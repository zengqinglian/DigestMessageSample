package eric.zeng.instinet.test.producer;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;

import eric.zeng.instinet.test.message.Message;

public class DummyMessageProducerTest {

	private BlockingQueue<Message> msgQueue = new LinkedBlockingQueue<>();

	private DummyMessageProducer producer;

	@Before
	public void setUp() {
		producer = new DummyMessageProducer();
		producer.setQueue(msgQueue);
	}

	@Test
	public void testReadFileAndPutIntoQueue() {
		producer.run();
		assertEquals(3, msgQueue.size());
	}
}
