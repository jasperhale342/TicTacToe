package Client;

import java.rmi.RemoteException;

import API.Client;
import API.GameServer;
import API.MatchmakerServer;
import State.GameState;

public class ClientManager implements Client {

    private MatchmakerServer mMatchmakerServer;
    private GameServer mGameServer;

    public ClientManager(MatchmakerServer matchmakerServer) {
        assert matchmakerServer != null;
        mMatchmakerServer = matchmakerServer;
    }

    @Override
    public void waitForGame(String waitMessage) throws RemoteException {
        System.out.println("Client will wait for game");
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

    public void sendFindGameRequest() {
        mMatchmakerServer.requestNewGame(this);
    }

}