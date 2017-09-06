package eric.zeng.instinet.test.consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eric.zeng.instinet.test.dispatcher.MessageDispatcher;
import eric.zeng.instinet.test.message.Message;
import eric.zeng.instinet.test.message.TradeMessage;

@RunWith(MockitoJUnitRunner.class)
public class MessageConsumerTest {
	private BlockingQueue<Message> msgQueue = new LinkedBlockingDeque<>();

	@Mock
	private MessageDispatcher dispatcher;

	private MessageConsumer messageConsumer;

	@Before
	public void setUp() {
		messageConsumer = new MessageConsumer();
		Message msg = new TradeMessage("test");
		msgQueue.add(msg);
		messageConsumer.setQueue(msgQueue);
		messageConsumer.setDispatcher(dispatcher);
	}

	@Test
	public void testConsumeMessage() throws InterruptedException {
		ArgumentCaptor<Message> message = ArgumentCaptor.forClass(Message.class);
		Thread t = new Thread(messageConsumer);
		t.start();
		Thread.sleep(10);
		verify(dispatcher, times(1)).onMessage(message.capture());
		assertEquals(message.getValue().getMsg(), "test");
	}
}
