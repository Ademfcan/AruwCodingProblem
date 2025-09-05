package Messages;

import Builders.MessageBuilder;
import Builders.VisionMessageBuilder;
import Builders.WheelVelocityMessageBuilder;
import Main.MessageReciever;

import java.util.stream.IntStream;

// Assumption: Wheel Velocity Data Is not more granular than 1 mm/s
public record WheelVelocityMessage(int leftSpeedMMs, int rightSpeedMMs) implements Message{
    public static final int PROTOCOL_ID = 1;

    static {
        // adding to the message reviever as a protocol
        MessageReciever.addProtocol(PROTOCOL_ID, WheelVelocityMessageBuilder::new);
    }

    @Override
    public IntStream toStream() {
        return IntStream.of(PROTOCOL_ID, leftSpeedMMs, rightSpeedMMs);
    }

}
