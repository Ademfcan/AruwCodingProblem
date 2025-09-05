package Builders;

import java.util.Optional;

// Once a message type id has been determined, all subsequent packets can be fed to the appropriate builder
// The packets will be fed until isFinished() returns true

public abstract class MessageBuilder <T>{
    public abstract boolean isFinished();
    public abstract T getBuiltMessage();
    public abstract Optional<T> tryBuildMessage(int nextPacket);
}
