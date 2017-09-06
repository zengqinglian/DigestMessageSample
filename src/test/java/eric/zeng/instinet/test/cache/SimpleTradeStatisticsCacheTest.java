package eric.zeng.instinet.test.cache;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import eric.zeng.instinet.test.service.TradeMessageService;
import eric.zeng.instinet.test.trade.Trade;

@RunWith(MockitoJUnitRunner.class)
public class SimpleTradeStatisticsCacheTest {
	@Mock
	private TradeMessageService tradeMessageService;

	@InjectMocks
	private SimpleTradeStatisticsCache cache;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(cache);
	}

	@Test
	public void testUpdate_InvalidTrade() {
		cache.update(null);
		assertEquals(0, cache.getAvgTradeCache().size());
		Trade trade = new Trade(1L, "test", 3.3, 1, null);
		cache.update(trade);
		assertEquals(0, cache.getAvgTradeCache().size());
		// other invalid case
	}

	@Test
	public void testUpdate_FirstRecord() {
		char[] flags = { 'A' };
		Trade trade = new Trade(1L, "test", 3.3, 1, flags);
		cache.update(trade);
		assertEquals("Unequal double", trade.getPrice(), cache.getAvgTradeCache().get("test"), 0.001);
		assertEquals(trade, cache.getLargestTradeCache().get("test"));
	}

	@Test
	public void testUpdate_UpdateRecord() {
		char[] flags = { 'A' };

		Trade trade1 = new Trade(1L, "test1", 3.2, 1, flags);
		Trade trade2 = new Trade(1L, "test2", 50, 1, flags);
		Trade trade3 = new Trade(1L, "test1", 3.1, 5, flags);

		cache.update(trade1);
		cache.update(trade2);
		cache.update(trade3);

		double avg = (trade1.getPrice() * trade1.getSize() + trade3.getPrice() * trade3.getSize())
				/ (trade1.getSize() + trade3.getSize());

		assertEquals("Unequal double", avg, cache.getAvgTradeCache().get("test1"), 0.001);
		assertEquals(trade3, cache.getLargestTradeCache().get("test1"));

	}
}
