package Network.Message;

import Game.Player;
import Game.Slot;
import GameEngine.utils.Position;

import java.io.Serializable;
import java.util.Map;

public class InitMessage implements Message, Serializable {

    private final Map<Position, Slot> slotMap;
    private final Player serverPlayer;
    private final Player clientPlayer;
    private final MessageType msgType = MessageType.INIT_MSG;

    public InitMessage(Map<Position, Slot> slotMap, Player serverPlayer, Player clientPlayer) {
        this.clientPlayer = clientPlayer;
        this.slotMap = slotMap;
        this.serverPlayer = serverPlayer;
    }


    public Map<Position, Slot> getSlotMap() {
        return slotMap;
    }

    public Player getServerPlayer() {
        return serverPlayer;
    }

    public Player getClientPlayer() {
        return clientPlayer;
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
        InitMessage other = (InitMessage) obj;

        return this.slotMap == other.getSlotMap() &&
                this.serverPlayer == other.getServerPlayer() &&
                this.clientPlayer == other.clientPlayer;
    }
}
