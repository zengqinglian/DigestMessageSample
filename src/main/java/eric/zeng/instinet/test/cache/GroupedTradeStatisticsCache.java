package eric.zeng.instinet.test.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eric.zeng.instinet.test.service.TradeMessageService;
import eric.zeng.instinet.test.trade.Trade;

@Component
public class GroupedTradeStatisticsCache implements TradeStatisticsCache {

	private static final Logger logger = LoggerFactory.getLogger(GroupedTradeStatisticsCache.class);

	private TradeMessageService tradeMessageService;

	private Map<String, Map<Character, List<Trade>>> groupBySymbolAndFlagCaches;

	@Autowired
	public GroupedTradeStatisticsCache(TradeMessageService tradeMessageService) {
		this.tradeMessageService = tradeMessageService;
		this.tradeMessageService.addStatisticsCache(this);
		groupBySymbolAndFlagCaches = new ConcurrentHashMap<>();
	}

	@Override
	public void update(Trade trade) {
		if (trade == null || trade.getFlags() == null || trade.getFlags().length == 0 || trade.getPrice() == 0
				|| trade.getSize() == 0 || trade.getSymbol().isEmpty() || trade.getTimeStamp() == 0) {
			logger.error("Invalid trade object passed in " + trade);
			return;
		}
		if (groupBySymbolAndFlagCaches.containsKey(trade.getSymbol())) {
			Map<Character, List<Trade>> map = groupBySymbolAndFlagCaches.get(trade.getSymbol());
			for (char c : trade.getFlags()) {
				if (map.containsKey(c)) {
					map.get(c).add(trade);
				} else {
					List<Trade> li = new ArrayList<>();
					li.add(trade);
					map.put(c, li);
				}
			}
		} else {
			Map<Character, List<Trade>> map = new ConcurrentHashMap<>();
			for (char c : trade.getFlags()) {
				List<Trade> li = new ArrayList<>();
				li.add(trade);
				map.put(c, li);
			}
			groupBySymbolAndFlagCaches.put(trade.getSymbol(), map);
		}
	}

	public Map<String, Map<Character, List<Trade>>> getGroupBySymbolAndFlagCaches() {
		return groupBySymbolAndFlagCaches;
	}

	public int getTradeNumberBySymbolAndFlag(String symbol, char c) {
		if (groupBySymbolAndFlagCaches.containsKey(symbol)) {
			Map<Character, List<Trade>> map = groupBySymbolAndFlagCaches.get(symbol);
			if (map.get(c) == null) {
				return 0;
			} else {
				return map.get(c).size();
			}
		} else {
			return 0;
		}
	}

}
