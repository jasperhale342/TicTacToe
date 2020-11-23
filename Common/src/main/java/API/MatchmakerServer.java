package API;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatchmakerServer extends Remote {

    public void requestNewGame(Client client) throws RemoteException;

    public void endGame(Client client) throws RemoteException;

}