package eric.zeng.instinet.test.exception;

public class MessageFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String errorCode = "Err001";
	private String message;

	public MessageFormatException() {
		super();
	}

	public MessageFormatException(String message) {
		super();
		this.message = message;
	}

	public MessageFormatException(String message, Exception e) {
		super(e);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
