package Network;

import java.io.*;
import java.net.Socket;


public class Client extends Connection implements IConnection{

    public boolean openConnection() {
        try {
            super.socket = new Socket(address, port);
            return super.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean close() {
        return super.close();
    }
}
