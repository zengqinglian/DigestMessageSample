package eric.zeng.instinet.test;

import eric.zeng.instinet.test.trade.Trade;

public class StatisticsResponse {

	private String symbol;
	private Trade largestTrade;
	private double avgPrice;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Trade getLargestTrade() {
		return largestTrade;
	}

	public void setLargestTrade(Trade largestTrade) {
		this.largestTrade = largestTrade;
	}

	public double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(double avgPrice) {
		this.avgPrice = avgPrice;
	}

}
