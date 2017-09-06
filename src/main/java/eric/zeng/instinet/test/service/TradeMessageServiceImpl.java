package eric.zeng.instinet.test.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import eric.zeng.instinet.test.cache.GroupedTradeStatisticsCache;
import eric.zeng.instinet.test.cache.SimpleTradeStatisticsCache;
import eric.zeng.instinet.test.cache.TradeStatisticsCache;
import eric.zeng.instinet.test.trade.Trade;

@Service
public class TradeMessageServiceImpl implements TradeMessageService {

	private Map<Class, TradeStatisticsCache> caches = new HashMap<>();

	@Override
	public Trade getLargestTradesBySymbol(String symbol) {
		if (caches.containsKey(SimpleTradeStatisticsCache.class)) {
			SimpleTradeStatisticsCache cache = (SimpleTradeStatisticsCache) (caches
					.get(SimpleTradeStatisticsCache.class));
			return cache.getLargestTradeCache().get(symbol);

		}
		return null;
	}

	@Override
	public double getAvgTradePriceBySymbol(String symbol) {
		if (caches.containsKey(SimpleTradeStatisticsCache.class)) {
			SimpleTradeStatisticsCache cache = (SimpleTradeStatisticsCache) (caches
					.get(SimpleTradeStatisticsCache.class));
			return cache.getAvgTradeCache().get(symbol) == null ? 0.0 : cache.getAvgTradeCache().get(symbol);

		}
		return 0.0;
	}

	@Override
	public int getNumberOfTradeBySymbolAndFlag(String symbol, char flag) {
		if (caches.containsKey(GroupedTradeStatisticsCache.class)) {
			GroupedTradeStatisticsCache cache = (GroupedTradeStatisticsCache) (caches
					.get(GroupedTradeStatisticsCache.class));

			return cache.getTradeNumberBySymbolAndFlag(symbol, flag);

		}
		return 0;
	}

	@Override
	public void generateStatistics(Trade trade) {
		for (TradeStatisticsCache cache : caches.values()) {
			cache.update(trade);
		}
	}

	@Override
	public void addStatisticsCache(TradeStatisticsCache cache) {
		caches.put(cache.getClass(), cache);
	}

	protected Map<Class, TradeStatisticsCache> getCaches() {
		return caches;
	}

}
