package Network.Message;

import java.io.Serializable;

public class EndTourMessage implements Message, Serializable {

    private final MessageType msgType = MessageType.END_TOUR_MSG;

    public MessageType getType(){
        return this.msgType;
    }

}
