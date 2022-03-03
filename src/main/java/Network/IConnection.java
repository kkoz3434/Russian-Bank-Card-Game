package Network;

import Network.Message.Message;

public interface IConnection {
    boolean openConnection();
    boolean send(Message message);
    Message receive();
    boolean close();
}
