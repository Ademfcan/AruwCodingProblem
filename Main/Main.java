package Main;

import Messages.WheelVelocityMessage;

public class Main
{
    public static void main(String[] args) {
        // test "sending" some messages

        WheelVelocityMessage message = new WheelVelocityMessage(1000, 2000);

        message.toStream().forEach(MessageReciever::recievePacket);

        WheelVelocityMessage sentMessage = (WheelVelocityMessage) MessageReciever.messagesQueue.poll();

        System.out.println("Sent Message: Left Speed = " + message.leftSpeedMMs() + ", Right Speed = " + message.rightSpeedMMs());
        System.out.println("Received Message: Left Speed = " + sentMessage.leftSpeedMMs() + ", Right Speed = " + sentMessage.rightSpeedMMs());
    }
}

