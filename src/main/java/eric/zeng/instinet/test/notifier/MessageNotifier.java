package eric.zeng.instinet.test.notifier;

import eric.zeng.instinet.test.message.Message;

public interface MessageNotifier {
	Class<? extends Message> getMessageType();

	void notify(Message msg);
}
