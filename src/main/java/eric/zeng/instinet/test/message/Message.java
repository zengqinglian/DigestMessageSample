package eric.zeng.instinet.test.message;

public interface Message {
	public static final String SPLITER = "%";

	public Class<? extends Message> getType();

	public String getMsg();

	public int getLength();
}
