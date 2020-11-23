package Game;

import java.rmi.RemoteException;

import API.Client;
import API.GameMove;
import API.GameServer;

public class GameManager implements GameServer {

    @Override
    public void requestMove(Client client, GameMove move) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestRematch(Client client) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendHeartbeat(Client client) throws RemoteException {
        // TODO Auto-generated method stub

    }


}