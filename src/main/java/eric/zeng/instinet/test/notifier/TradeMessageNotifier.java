package eric.zeng.instinet.test.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eric.zeng.instinet.test.dispatcher.MessageDispatcher;
import eric.zeng.instinet.test.exception.MessageFormatException;
import eric.zeng.instinet.test.message.Message;
import eric.zeng.instinet.test.message.TradeMessage;
import eric.zeng.instinet.test.service.TradeMessageService;
import eric.zeng.instinet.test.trade.Trade;

@Component
public class TradeMessageNotifier implements MessageNotifier {

	private static final Logger logger = LoggerFactory.getLogger(TradeMessageNotifier.class);

	private MessageDispatcher dispatcher;

	private TradeMessageService messageService;

	@Autowired
	public TradeMessageNotifier(MessageDispatcher dispatcher, TradeMessageService messageService) {
		this.dispatcher = dispatcher;
		this.messageService = messageService;
		this.dispatcher.addNotifier(this);
	}

	@Override
	public Class<? extends Message> getMessageType() {
		return TradeMessage.class;
	}

	@Override
	public void notify(Message msg) {
		if (msg.getType().equals(TradeMessage.class)) {
			try {
				messageService.generateStatistics(convert(msg));
			} catch (MessageFormatException e) {
				logger.error("invalid message received : " + msg.getLength(), e);
			}
		} else {
			logger.error("wrong message type passed in expect :" + TradeMessage.class + " but get :" + msg.getType());
		}
	}

	// it is better to create a mapper to do the conversion. there are some lib
	// can be used as a mapper
	private Trade convert(Message msg) throws MessageFormatException {
		String[] infos = msg.getMsg().split(Message.SPLITER);
		if (validateMessage(infos, msg)) {
			return createTrade(infos);
		}

		throw new MessageFormatException(
				"Invalid message field, expect :" + msg.getLength() + " but actual:" + infos.length);
	}

	// assume every trade message will have values in all fields.no option field
	// so no need builder pattern
	private Trade createTrade(String[] infos) throws MessageFormatException {
		try {
			long timeStamp = Long.valueOf(infos[0]);
			String symbol = infos[1];
			if (symbol.isEmpty()) {
				throw new MessageFormatException("empty symbol value in the message");
			}
			double price = Double.valueOf(infos[2]);
			int size = Integer.valueOf(infos[3]);
			char[] flags = convertToFlags(infos[4]);

			return new Trade(timeStamp, symbol, price, size, flags);
		} catch (NumberFormatException e) {
			throw new MessageFormatException("Invalid number in timeStamp and/or size", e);
		}
	}

	private char[] convertToFlags(String flag) throws MessageFormatException {
		if (flag.isEmpty()) {
			throw new MessageFormatException("empty trade flag in the message:" + flag);
		}
		String[] flags = flag.split(",");
		char[] cFlag = new char[flags.length];
		for (int i = 0; i < flags.length; i++) {
			if (flags[i].trim().length() != 1) {
				throw new MessageFormatException("Invalid trade flag in the message:" + flag);
			} else {
				cFlag[i] = flags[i].charAt(0);
			}
		}
		return cFlag;
	}

	private boolean validateMessage(String[] infos, Message msg) {
		return infos.length == msg.getLength();
	}

}
