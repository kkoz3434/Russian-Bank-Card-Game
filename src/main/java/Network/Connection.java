package Network;

import Network.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public abstract class Connection {
    protected ObjectOutputStream out = null;
    protected ObjectInputStream in = null;
    protected String address = "127.0.0.1";
    protected int port = 2137;
    protected Socket socket;


    public boolean send(Message message) {
        try {
            out.writeObject(message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Message receive() {
        try {
            waitForInput();
            return (Message)in.readObject();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return null;
    }

    protected boolean openConnection(){
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean close() {
        try {
            out.close();
            socket.close();
            in.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void waitForInput(){
        while (in==null){
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
