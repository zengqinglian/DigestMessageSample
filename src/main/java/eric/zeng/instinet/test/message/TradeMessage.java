package eric.zeng.instinet.test.message;

public class TradeMessage implements Message {

	private static final int MESSAGE_LENGTH = 5;
	private String msg;

	public TradeMessage(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

	@Override
	public Class<? extends Message> getType() {
		return TradeMessage.class;
	}

	@Override
	public String toString() {
		return "TradeMessage [msg=" + msg + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
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
		TradeMessage other = (TradeMessage) obj;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		return true;
	}

	@Override
	public int getLength() {
		return MESSAGE_LENGTH;
	}

}
