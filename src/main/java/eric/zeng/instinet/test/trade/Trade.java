package eric.zeng.instinet.test.trade;

import java.util.Arrays;

public class Trade {
	private long timeStamp;
	private String symbol;
	private double price;
	private int size;
	private char[] flags;

	public Trade(long timeStamp, String symbol, double price, int size, char[] flags) {
		this.timeStamp = timeStamp;
		this.symbol = symbol;
		this.price = price;
		this.size = size;
		this.flags = flags;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public String getSymbol() {
		return symbol;
	}

	public double getPrice() {
		return price;
	}

	public int getSize() {
		return size;
	}

	// defence copy make sure the flag is immutable
	public char[] getFlags() {
		return flags == null ? null : flags.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(flags);
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + size;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (!Arrays.equals(flags, other.flags))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (size != other.size)
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trade [timeStamp=" + timeStamp + ", symbol=" + symbol + ", price=" + price + ", size=" + size
				+ ", flags=" + Arrays.toString(flags) + "]";
	}
}
