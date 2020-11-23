package Client;

import java.rmi.RemoteException;

import API.Client;
import API.GameServer;
import State.GameState;

public class ClientManager implements Client {

    @Override
    public void waitForGame(String waitMessage) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void startGame(GameServer gameServer, GameState initialState) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateGame(GameState newState) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void endGame(GameState endState) throws RemoteException {
        // TODO Auto-generated method stub

    }

}