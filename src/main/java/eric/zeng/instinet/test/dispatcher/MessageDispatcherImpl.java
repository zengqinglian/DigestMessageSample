package eric.zeng.instinet.test.dispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import eric.zeng.instinet.test.message.Message;
import eric.zeng.instinet.test.notifier.MessageNotifier;

@Component
public class MessageDispatcherImpl implements MessageDispatcher {

	private List<MessageNotifier> notifiers = new ArrayList<>();

	@Override
	public void onMessage(Message msg) {
		List<MessageNotifier> msgTypeNotifiers = notifiers.stream()
				.filter(x -> x.getMessageType().equals(msg.getType())).collect(Collectors.toList());
		for (MessageNotifier n : msgTypeNotifiers) {
			n.notify(msg);
		}
	}

	@Override
	public void addNotifier(MessageNotifier notifier) {
		notifiers.add(notifier);
	}

}
