package Command;


public interface Command {
    void execute();

    String getName();

    void undo();

    public default void redo() {
        execute();
    }

}
