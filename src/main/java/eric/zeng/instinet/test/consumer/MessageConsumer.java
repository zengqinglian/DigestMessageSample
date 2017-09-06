package eric.zeng.instinet.test.consumer;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eric.zeng.instinet.test.dispatcher.MessageDispatcher;
import eric.zeng.instinet.test.message.Message;

@Component
public class MessageConsumer implements Runnable {

	@Autowired
	private BlockingQueue<Message> msgQueue;

	@Autowired
	private MessageDispatcher dispatcher;

	private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

	public MessageConsumer() {
	}

	protected void setQueue(BlockingQueue<Message> msgQueue) {
		this.msgQueue = msgQueue;
	}

	protected void setDispatcher(MessageDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Message msg = msgQueue.take();
				dispatcher.onMessage(msg);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
				logger.error("TradeMessageConsumer is interrupted:", ex);
			}
		}
	}

}
