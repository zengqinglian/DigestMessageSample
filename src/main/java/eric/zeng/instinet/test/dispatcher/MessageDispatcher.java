package eric.zeng.instinet.test.dispatcher;

import eric.zeng.instinet.test.message.Message;
import eric.zeng.instinet.test.notifier.MessageNotifier;

public interface MessageDispatcher {
	void onMessage(Message msg);

	void addNotifier(MessageNotifier listener);
}
