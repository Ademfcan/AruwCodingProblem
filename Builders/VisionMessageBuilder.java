package Builders;

import Messages.VisionTargetMessage;

import java.util.Optional;

public class VisionMessageBuilder extends MessageBuilder<VisionTargetMessage> {

    private VisionTargetMessage message = null;
    private boolean isFinished = false;

    private int numTargets = -1;

    private int currentTarget = 0;
    private boolean readingX = true;
    private VisionTargetMessage.VisionTarget[] targets = null;

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public VisionTargetMessage getBuiltMessage() {
        if (isFinished()){
            return message;
        }
        throw new IllegalStateException("Message not finished yet!");
    }

    @Override
    public Optional<VisionTargetMessage> tryBuildMessage(int nextPacket) {
        if(isFinished()){
            throw new IllegalStateException("Message already finished!");
        }

        // first packet is number of targets
        // subsequent packets are target coordinates (x, y)
        if (numTargets == -1){

            // first packet, number of targets
            numTargets = nextPacket;
            // also initialize targets array
            targets = new VisionTargetMessage.VisionTarget[numTargets];

            // initialize all targets to (0, 0)
            for(int i = 0; i < numTargets; i++){
                targets[i] = VisionTargetMessage.VisionTarget.CreateDefault();
            }

            return Optional.empty();
        }

        // now reading target coordinates

        if(readingX){
            // reading x coordinate of current target
            targets[currentTarget].x = nextPacket;
            readingX = false; // next packet will be y
        } else {
            // reading y coordinate of current target
            targets[currentTarget].y = nextPacket;
            readingX = true; // next packet will be x of next target

            // also increment current target, as we have finished reading this target
            currentTarget++;
        }

        if(currentTarget == numTargets){
            // finished reading all targets
            message = new VisionTargetMessage(targets);
            isFinished = true;
            return Optional.of(message);
        }
        else{
            return Optional.empty();
        }

    }
}
