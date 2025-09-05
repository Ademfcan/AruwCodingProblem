package Main;

import Builders.MessageBuilder;
import Messages.Message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Supplier;

public class MessageReciever {
    // queue of completed incoming messages
    public static final Queue<Message> messagesQueue = new LinkedList<>();

    // store all prptocol ids and their corresponding message builders
    private static final Map<Integer, Supplier<MessageBuilder<? extends Message>>> protocols = new HashMap<>();

    // current message being built
    private static MessageBuilder<? extends Message> currentMessageBuilder = null;


    public static void addProtocol(int protocolId, Supplier<MessageBuilder<? extends Message>> messageBuilder) {
        if(protocols.containsKey(protocolId)){
            throw new IllegalArgumentException("Protocol ID already registered: " + protocolId);
        }

        protocols.put(protocolId, messageBuilder);
    }


    public static void recievePacket(int packet) {
        // if no current message builder, this packet should be a protocol ID
        if (currentMessageBuilder == null) {
            if (!protocols.containsKey(packet)) {
                throw new IllegalArgumentException("Unknown protocol ID: " + packet);
            }
            // create a new message builder for this protocol
            currentMessageBuilder = protocols.get(packet).get();
            return;
        }

        // otherwise, pass the packet to the current message builder
        var builtMessage = currentMessageBuilder.tryBuildMessage(packet);
        if (builtMessage.isPresent()) {
            // message is complete, add to queue
            messagesQueue.add(builtMessage.get());
            // reset current message builder
            currentMessageBuilder = null;
        }
    }




}
