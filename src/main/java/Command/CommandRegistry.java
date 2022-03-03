package Command;

import Game.Table;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class  CommandRegistry{

    private final ObservableList<Command> commandStack = FXCollections.observableArrayList();
    private final ObservableList<Command> commandRedoStack = FXCollections.observableArrayList();
    private final Table table;


    @Inject
    public CommandRegistry(Table table) {
        this.table = table;
    }

    public synchronized void executeCommand(Command command) {
        command.execute();
        commandStack.add(command);
        commandRedoStack.clear();
    }

    public synchronized void redo() {
        if(!commandRedoStack.isEmpty()){
            System.out.println("redo clicked");
            Command tmp = commandRedoStack.get(commandRedoStack.size() - 1);
            tmp.execute();
            commandRedoStack.remove(commandRedoStack.size() - 1);
            commandStack.add(tmp);
        }
    }

    public synchronized void undo() {
        if (!commandStack.isEmpty()){
            System.out.println("undo clicked");
            Command tmp = commandStack.get(commandStack.size() - 1);
            tmp.undo();
            commandStack.remove(tmp);
            commandRedoStack.add(tmp);
        }
    }

    public ObservableList<Command> getCommandStack() {
        return commandStack;
    }

    public ObservableList<Command> getCommandRedoStack() {
        return commandRedoStack;
    }
}
