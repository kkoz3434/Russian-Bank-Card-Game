package Network.Message;

import GameEngine.utils.Position;
import javafx.util.Pair;

import java.io.Serializable;

public class PositionMessage implements Message, Serializable {

    private final Pair<Position, Position> messageContent;
    private final MessageType msgType = MessageType.POSITION_MSG;

    public PositionMessage(Position from, Position to){
        this.messageContent = new Pair<>(from, to);
    }

    public Position getFrom(){
        return this.messageContent.getKey();
    }
    public Position getTo(){
        return this.messageContent.getValue();
    }

    @Override
    public MessageType getType(){
        return this.msgType;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PositionMessage other = (PositionMessage) obj;

        return this.getFrom().row() == other.getFrom().row() &&
                this.getFrom().col() == other.getFrom().col() &&
                this.getTo().row() == other.getTo().row() &&
                this.getTo().row() == other.getTo().row();
    }

}
