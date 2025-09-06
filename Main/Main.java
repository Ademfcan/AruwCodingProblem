package Main;

import Messages.Message;
import Messages.VisionTargetMessage;
import Messages.WheelVelocityMessage;

public class Main
{
    public static void main(String[] args) {
        // test "sending" some messages
        Message[] messages = {
                new WheelVelocityMessage(1000, 2000),
                new VisionTargetMessage(new VisionTargetMessage.VisionTarget[]{
                        new VisionTargetMessage.VisionTarget(1, 0),
                        new VisionTargetMessage.VisionTarget(2, 1)
                }),
                new WheelVelocityMessage(-500, 1500),
                new VisionTargetMessage(new VisionTargetMessage.VisionTarget[]{
                        new VisionTargetMessage.VisionTarget(1, 0),
                        new VisionTargetMessage.VisionTarget(2, 1)
                }),
        };

        for (Message message : messages) {
            message.toStream().forEach(MessageReciever::recievePacket);
        }

        // verify messages were received correctly, eg in order and with correct data

        for (Message message : messages) {
            if(MessageReciever.messagesQueue.isEmpty()) {
                throw new IllegalStateException("Message queue is empty, expected more messages!");
            }

            if(MessageReciever.messagesQueue.poll().equals(message)){
                System.out.println("Message received correctly: " + message);
            }
            else{
                throw new IllegalStateException("Received message does not match sent message! Sent: " + message);
            }
        }

    }
}

