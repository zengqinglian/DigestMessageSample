package eric.zeng.instinet.test.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eric.zeng.instinet.test.message.Message;
import eric.zeng.instinet.test.message.TradeMessage;

//mock message producer to send message to MQ
@Component
public class DummyMessageProducer implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(DummyMessageProducer.class);

	@Autowired
	private BlockingQueue<Message> msgQueue;

	public DummyMessageProducer() {
	}

	protected void setQueue(BlockingQueue<Message> msgQueue) {
		this.msgQueue = msgQueue;
	}

	@Override
	public void run() {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
					new InputStreamReader(getClass().getClassLoader().getResourceAsStream("trades.csv")));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				msgQueue.put(new TradeMessage(line));
			}
		} catch (IOException e) {
			logger.error("failed to read file trades.csv", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.error("DummyMessageProducer is interrupted:", e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error("failed to close buffer reader", e);
				}
			}
		}
	}
}
