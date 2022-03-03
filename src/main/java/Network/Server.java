package Network;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Connection implements IConnection {

    private ServerSocket server = null;

    public boolean openConnection(){
        try {
            server = new ServerSocket(port);
            super.socket = server.accept();
            return super.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean close() {
        try {
            server.close();
            return super.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
