package Messages;

import Builders.MessageBuilder;

public interface Buildable <T> {
    MessageBuilder<T> createBuilder();
}
