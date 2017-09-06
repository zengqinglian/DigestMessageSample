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
public class GroupedTradeStatisticsCacheTest {
	@Mock
	private TradeMessageService tradeMessageService;

	@InjectMocks
	private GroupedTradeStatisticsCache cache;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(cache);
	}

	@Test
	public void testUpdate_InvalidTrade() {
		cache.update(null);
		assertEquals(0, cache.getGroupBySymbolAndFlagCaches().size());
		Trade trade = new Trade(1L, "test", 3.3, 1, null);
		cache.update(trade);
		assertEquals(0, cache.getGroupBySymbolAndFlagCaches().size());
		// other invalid case
	}

	@Test
	public void testUpdate() {
		char[] flags1 = { 'A' };
		char[] flags2 = { 'A', 'B' };
		char[] flags3 = { 'B' };
		Trade trade1 = new Trade(1L, "test", 3.3, 1, flags1);
		Trade trade2 = new Trade(1L, "test", 3.3, 2, flags2);
		Trade trade3 = new Trade(1L, "test", 3.3, 3, flags3);

		cache.update(trade1);
		// TODO: check result
		cache.update(trade2);
		// TODO: check result
		cache.update(trade3);

		assertEquals(2, cache.getTradeNumberBySymbolAndFlag("test", 'A'));
		assertEquals(2, cache.getTradeNumberBySymbolAndFlag("test", 'B'));
		assertEquals(0, cache.getTradeNumberBySymbolAndFlag("test", 'C'));

	}

}
