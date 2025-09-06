package Messages;

import Builders.MessageBuilder;
import Builders.VisionMessageBuilder;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

import Main.MessageReciever;

public record VisionTargetMessage(VisionTarget[] targets) implements Message {
    public static final int PROTOCOL_ID = 2;

    static {
        // adding to the message reviever as a protocol
        MessageReciever.addProtocol(PROTOCOL_ID, VisionMessageBuilder::new);
    }

    @Override
    public IntStream toStream() {
        // Format: [PROTOCOL_ID, numTargets, x1, y1, x2, y2, ...]
        return IntStream.concat(
                // header (protocol ID and number of targets)
                IntStream.of(PROTOCOL_ID, targets.length),
                // flatten each target's coordinates, eg (x1, y1, x2, y2, ...
                Arrays.stream(targets).flatMapToInt(vt -> IntStream.of(vt.x, vt.y))
        );

    }

    @Override
    public String toString() {
        return "VisionTargetMessage{" +
                "targets=" + Arrays.toString(targets) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VisionTargetMessage that = (VisionTargetMessage) o;
        return Objects.deepEquals(targets, that.targets);
    }

    public static class VisionTarget{
        public int x;
        public int y;

        public VisionTarget(int x, int y){
            this.x = x;
            this.y = y;
        }

        public static VisionTarget CreateDefault(){
            final int defaultValue = 0;
            return new VisionTarget(defaultValue,defaultValue);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            VisionTarget that = (VisionTarget) o;
            return x == that.x && y == that.y;
        }

        @Override
        public String toString() {
            return "VisionTarget{" +
                    "y=" + y +
                    ", x=" + x +
                    '}';
        }
    }
}
