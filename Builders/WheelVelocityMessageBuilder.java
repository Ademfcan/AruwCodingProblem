package Builders;

import Messages.WheelVelocityMessage;

import java.util.Optional;

public class WheelVelocityMessageBuilder extends MessageBuilder<WheelVelocityMessage> {

    private WheelVelocityMessage message = null;
    private boolean isFinished = false;

    private int leftVelocityMMs = -1;

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public WheelVelocityMessage getBuiltMessage() {
        if (isFinished()){
            return message;
        }
        throw new IllegalStateException("Message not finished yet!");
    }


    @Override
    public Optional<WheelVelocityMessage> tryBuildMessage(int nextPacket) {
        if(isFinished()){
            throw new IllegalStateException("Message already finished!");
        }

        // start with left velocity, then right velocity
        if(leftVelocityMMs == -1){
            leftVelocityMMs = nextPacket;
            return Optional.empty();
        }
        else{
            // if not left, it must be right
            int rightVelocityMMs = nextPacket;

            // now we have both velocities, build the message
            message = new WheelVelocityMessage(leftVelocityMMs, rightVelocityMMs);
            isFinished = true;
            return Optional.of(message);
        }

    }
}
