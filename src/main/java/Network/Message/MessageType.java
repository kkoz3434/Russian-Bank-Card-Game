package Network.Message;

public enum MessageType {
    INIT_MSG("init_message"),
    POSITION_MSG("position_message"),
    END_TOUR_MSG("end_tour_message");

    private String value;

    MessageType(String name){
        this.value = name;
    }

    public String getValue(){
        return value;
    }

}
