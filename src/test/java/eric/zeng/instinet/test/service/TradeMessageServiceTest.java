package eric.zeng.instinet.test.service;

import static org.mockito.Matchers.any;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import eric.zeng.instinet.test.cache.GroupedTradeStatisticsCache;
import eric.zeng.instinet.test.cache.SimpleTradeStatisticsCache;
import eric.zeng.instinet.test.cache.TradeStatisticsCache;
import eric.zeng.instinet.test.trade.Trade;

@RunWith(MockitoJUnitRunner.class)
public class TradeMessageServiceTest {

	private TradeMessageServiceImpl messageService;

	@Mock
	private SimpleTradeStatisticsCache simpleCache;

	@Mock
	private GroupedTradeStatisticsCache groupCache;

	@Before
	public void setUp() {
		messageService = new TradeMessageServiceImpl();
		Map<Class, TradeStatisticsCache> caches = messageService.getCaches();
		caches.put(SimpleTradeStatisticsCache.class, simpleCache);
		caches.put(GroupedTradeStatisticsCache.class, groupCache);
	}

	@Test
	public void testGetLargestTradesBySymbol() {
		messageService.getLargestTradesBySymbol("test");
		Mockito.verify(simpleCache).getLargestTradeCache();
	}

	@Test
	public void testGetAvgTradePriceBySymbol() {
		messageService.getAvgTradePriceBySymbol("test");
		Mockito.verify(simpleCache).getAvgTradeCache();
	}

	@Test
	public void testGetNumberOfTradeBySymbolAndFlag() {
		messageService.getNumberOfTradeBySymbolAndFlag("test", 'A');
		Mockito.verify(groupCache).getTradeNumberBySymbolAndFlag("test", 'A');
	}

	@Test
	public void testGenerateStatistics() {
		messageService.generateStatistics(Mockito.mock(Trade.class));
		Mockito.verify(simpleCache).update(any(Trade.class));
		Mockito.verify(groupCache).update(any(Trade.class));
	}
}
