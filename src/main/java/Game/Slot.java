package Game;

import GameEngine.utils.State;

import java.io.Serializable;
import java.util.Optional;

public class Slot implements Serializable {
    // why are we using this class? Don't know
    // now we know
    private Stack stack;
    private String name;
    private State state;

    public Slot(Stack stack, String name, State state) {
        this.name = name;
        this.stack = stack;
        this.state= state;
    }

    public Slot(State state) {
        this(new Stack(false), "NoNameSlot", state);
    }
    public Slot(State state, boolean isHidden) {
        this(new Stack(isHidden), "NoNameSlot", state);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public boolean isEmpty(){
        return this.stack.getCards().isEmpty();
    }

    public Optional<Card> peek(){
        return stack.peek();
    }

    @Override
    public String toString() {
        return name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Slot slot2 = (Slot) o;
        return slot2.getState() == this.getState() && slot2.stack == this.stack;
    }

}
