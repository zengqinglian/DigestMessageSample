package eric.zeng.instinet.test.service;

import eric.zeng.instinet.test.cache.TradeStatisticsCache;
import eric.zeng.instinet.test.trade.Trade;

public interface TradeMessageService {
	void generateStatistics(Trade trade);

	Trade getLargestTradesBySymbol(String symbol);

	void addStatisticsCache(TradeStatisticsCache cache);

	double getAvgTradePriceBySymbol(String symbol);

	int getNumberOfTradeBySymbolAndFlag(String symbol, char flag);
}
