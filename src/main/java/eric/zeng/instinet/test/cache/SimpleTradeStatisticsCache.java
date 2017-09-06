package eric.zeng.instinet.test.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eric.zeng.instinet.test.service.TradeMessageService;
import eric.zeng.instinet.test.trade.Trade;

@Component
public class SimpleTradeStatisticsCache implements TradeStatisticsCache {

	private static final Logger logger = LoggerFactory.getLogger(SimpleTradeStatisticsCache.class);

	private TradeMessageService tradeMessageService;

	private Map<String, Trade> largestTradecache;
	private Map<String, Double> totalCache;
	private Map<String, Integer> sizeCache;
	private Map<String, Double> avgCache;

	@Autowired
	public SimpleTradeStatisticsCache(TradeMessageService tradeMessageService) {
		this.tradeMessageService = tradeMessageService;
		this.tradeMessageService.addStatisticsCache(this);
		largestTradecache = new ConcurrentHashMap<>();
		totalCache = new ConcurrentHashMap<>();
		sizeCache = new ConcurrentHashMap<>();
		avgCache = new ConcurrentHashMap<>();
	}

	@Override
	public void update(Trade trade) {
		if (trade == null || trade.getFlags() == null || trade.getFlags().length == 0 || trade.getPrice() == 0
				|| trade.getSize() == 0 || trade.getSymbol().isEmpty() || trade.getTimeStamp() == 0) {
			logger.error("Invalid trade object passed in " + trade);
			return;
	}
		double newTotal = trade.getPrice() * trade.getSize();
		if (avgCache.containsKey(trade.getSymbol())) {
			Trade currentLargest = largestTradecache.get(trade.getSymbol());
			double currentVal = currentLargest.getPrice() * currentLargest.getSize();
			totalCache.put(trade.getSymbol(), totalCache.get(trade.getSymbol()) + newTotal);
			sizeCache.put(trade.getSymbol(), sizeCache.get(trade.getSymbol()) + trade.getSize());
			avgCache.put(trade.getSymbol(), totalCache.get(trade.getSymbol()) / sizeCache.get(trade.getSymbol()));
			if (newTotal >= currentVal) {
				// assume equal situation will return latest trade
				largestTradecache.put(trade.getSymbol(), trade);
			}
		} else {
			largestTradecache.put(trade.getSymbol(), trade);
			totalCache.put(trade.getSymbol(), newTotal);
			sizeCache.put(trade.getSymbol(), trade.getSize());
			avgCache.put(trade.getSymbol(), trade.getPrice());
		}
	}

	public Map<String, Trade> getLargestTradeCache() {
		return largestTradecache;
	}

	public Map<String, Double> getAvgTradeCache() {
		return avgCache;
	}

}
