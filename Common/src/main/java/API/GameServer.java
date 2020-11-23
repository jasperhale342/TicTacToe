package API;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServer extends Remote {

    public void requestMove(Client client, GameMove move) throws RemoteException;

    public void requestRematch(Client client) throws RemoteException;

    public void sendHeartbeat(Client client) throws RemoteException;

}