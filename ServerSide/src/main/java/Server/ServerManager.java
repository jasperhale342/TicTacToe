package Server;

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
        mClients = new ArrayList<>();
        mUnmatchedClients = new LinkedList<>();
        mGameRequests = new LinkedList<>();
        mGameManagers = new ArrayList<>();
        mCancelFlag.set(false);
    }

    public void respondToRequests() {
        while (true) {
            if (mCancelFlag.get())
                break;
            if (mGameRequests.size() < 1)
                continue;
            final Client client = mGameRequests.poll();
            try {
                client.waitForGame(WAIT_MSG);
                mUnmatchedClients.add(client);
                System.out.println(client + " placed in the lobby.");
            } catch (Exception e) {
                mGameRequests.add(client);
                e.printStackTrace();
            }
        }
    }

    public void findGames() {
        while (true) {
            if (mCancelFlag.get())
                break;
            if (mUnmatchedClients.size() < 2)
                continue;

            System.out.println("There are at least two clients waiting for a game.");

            final Client client1 = mUnmatchedClients.poll();
            final Client client2 = mUnmatchedClients.poll();
            final GameManager gameMgr = new GameManager(this, client1, client2);
            mGameManagers.add(gameMgr);
            new Thread(gameMgr::processGameMoveRequests).start();
            client1.startGame(gameMgr, gameMgr.getGameState());
            client2.startGame(gameMgr, gameMgr.getGameState());
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
    public void endMatch(Client client) {

    }

}