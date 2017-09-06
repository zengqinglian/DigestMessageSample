package eric.zeng.instinet.test.cache;

import eric.zeng.instinet.test.trade.Trade;

public interface TradeStatisticsCache {
	void update(Trade trade);
}
