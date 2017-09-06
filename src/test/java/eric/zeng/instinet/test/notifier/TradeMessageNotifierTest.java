package eric.zeng.instinet.test.notifier;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import eric.zeng.instinet.test.dispatcher.MessageDispatcher;
import eric.zeng.instinet.test.message.TradeMessage;
import eric.zeng.instinet.test.service.TradeMessageService;
import eric.zeng.instinet.test.trade.Trade;

@RunWith(MockitoJUnitRunner.class)
public class TradeMessageNotifierTest {

	@Mock
	private MessageDispatcher dispatcher;

	@Mock
	private TradeMessageService messageService;

	@InjectMocks
	private TradeMessageNotifier notifier;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(notifier);
	}

	@Test
	public void testNotify_InvalidMissingField() {
		TradeMessage message1 = new TradeMessage("1481107485791%XYZ LN%200.01%968");
		notifier.notify(message1);
		Mockito.verify(messageService, never()).generateStatistics(any());
	}

	@Test
	public void testNotify_InvalidEmptySymbol() {
		TradeMessage message1 = new TradeMessage("1481107485791% %200.01%968");
		notifier.notify(message1);
		Mockito.verify(messageService, never()).generateStatistics(any());
	}

	@Test
	public void testNotify_InvalidEmptyFlags() {
		TradeMessage message1 = new TradeMessage("1481107485791%XYZ LN%200.01%968%");
		notifier.notify(message1);
		Mockito.verify(messageService, never()).generateStatistics(any());
	}

	@Test
	public void testNotify_InvalidZeroPrice() {
		TradeMessage message1 = new TradeMessage("1481107485791%XYZ LN%200.01%968%");
		notifier.notify(message1);
		Mockito.verify(messageService, never()).generateStatistics(any());
	}

	@Test
	public void testNotify_Success() {
		TradeMessage message1 = new TradeMessage("1481107485791%XYZ LN%200.01%968%Z");
		notifier.notify(message1);
		char[] flags = { 'Z' };
		Mockito.verify(messageService, times(1))
				.generateStatistics(new Trade(1481107485791L, "XYZ LN", 200.01, 968, flags));
	}

}
