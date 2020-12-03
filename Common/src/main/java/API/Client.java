package API;

import java.rmi.Remote;
import java.rmi.RemoteException;

import State.GameState;

public interface Client{

    public void waitForGame(String waitMessage);

    public void startGame(GameServer gameServer, GameState initialState);

    public void updateGame(GameState newState);

    public void endGame(GameState endState);

    public String getPlayerName();

}