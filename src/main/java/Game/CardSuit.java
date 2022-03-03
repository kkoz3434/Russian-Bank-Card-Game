package Game;

import java.io.Serializable;

public enum CardSuit implements Serializable {
    CLUBS("clubs","black"),
    DIAMONDS("diamonds","red"),
    HEARTS("hearts","red"),
    SPADES("spades","black");

    String name;
    String color;

    CardSuit(String name, String color) {
        this.name = name;
        this.color = color;
    }
    public String getString(){
        return this.name;
    }
    public String getColor(){return this.color;}
}
