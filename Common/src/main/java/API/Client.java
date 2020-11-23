package API;

import java.rmi.Remote;
import java.rmi.RemoteException;

import State.GameState;

public interface Client extends Remote {

    public void waitForGame(String waitMessage) throws RemoteException;

    public void startGame(GameServer gameServer, GameState initialState) throws RemoteException;

    public void updateGame(GameState newState) throws RemoteException;

    public void endGame(GameState endState) throws RemoteException;

}