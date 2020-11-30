package Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

import API.Client;
import API.MatchmakerServer;
import Game.GameManager;

public class ServerManager implements MatchmakerServer {

    private static final String WAIT_MSG = "Please wait while a game is found.";

    private final List<Client> mClients;
    private final Queue<Client> mUnmatchedClients;
    private final Queue<Client> mGameRequests;
    private final List<GameManager> mGameManagers;
    private final Object mMutex = new Object();
    private final AtomicBoolean mCancelFlag = new AtomicBoolean();

    public ServerManager() {
        mClients = new ArrayList<Client>();
        mUnmatchedClients = new LinkedList<Client>();
        mGameRequests = new LinkedList<Client>();
        mGameManagers = new ArrayList<GameManager>();
        mCancelFlag.set(false);
    }

    public void respondToRequests() {
        while (true) {
            if (mCancelFlag.get() == true)
                break;
            if (mGameRequests.size() < 1)
                continue;
            final Client client = mGameRequests.poll();
            try {
                client.waitForGame(WAIT_MSG);
                mUnmatchedClients.add(client);
                System.out.println(client + " placed in the lobby.");
            } catch (RemoteException e) {
                mGameRequests.add(client);
                e.printStackTrace();
            }
        }
    }

    public void findGames() {
        while (true) {
            if (mCancelFlag.get() == true)
                break;
            if (mUnmatchedClients.size() < 2)
                continue;
            final Client client1 = mUnmatchedClients.poll();
            final Client client2 = mUnmatchedClients.poll();

            //TODO: Create GameManager then call startGame on clients

            System.out.println("There are at least two clients waiting for a game.");
        }
    }

    public void cancel() {
        mCancelFlag.set(true);
    }

    @Override
    public void requestNewGame(Client client) {
        assert client != null;
        synchronized (mMutex) {
            if (!mClients.contains(client)) {
                mClients.add(client);
                mGameRequests.add(client);
                System.out.println(client + " has requested a new game.");
            }
        }
    }

    @Override
    public void endGame(Client client) {

    }

}