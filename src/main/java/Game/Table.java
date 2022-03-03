package Game;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.util.Pair;

import java.util.*;

public class Table {

    private final List<Slot> slots = new ArrayList<>();

    private final int numberOfSlots = 22;


    @Inject
    public Table() {

        //initTable();
        //initPlayers();

    }
    private void initTable(){
    }

    public List<Slot> getSlots() {
        return slots;
    }


    public void addStack(Slot slot, Stack stack) {
        if (!slot.getStack().getCards().isEmpty()) {
            slot.getStack().push(stack.getCards());
        } else {
            int index = slots.indexOf(slot);
            slots.get(index).getStack().push(stack.getCards());
        }
    }

    public void addStack(int slotId, Stack stack) {
        slots.get(slotId).getStack().push(stack.getCards());
    }

    public void clearStack(Slot slot) {
        int index = slots.indexOf(slot);
        slots.get(index).getStack().clear();
    }

    public void setStackOnSlot(Slot slot, Stack stack) {
        int index = slots.indexOf(slot);
        slots.get(index).setStack(stack);
    }
    public void addSlot(Slot slot){
        slots.add(slot);
    }
}
