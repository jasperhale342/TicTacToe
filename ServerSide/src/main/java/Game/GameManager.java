package Game;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import API.Client;
import API.GameMove;
import API.GameServer;
import Game.Utility.RuleChecker;
import Game.Utility.TTTStateBuilder;
import Pieces.OPiece;
import Pieces.Piece;
import Pieces.XPiece;
import Server.ServerManager;
import State.GameState;
import State.TTTState;

public class GameManager implements GameServer {

    private final ServerManager mServerManager;
    private final Client mClientOne;
    private final Client mClientTwo;
    private Client mClientWithTurn;
    private final Map<Client, Queue<GameMove>> mClientRequests;
    private GameState mGameState;
    private final AtomicBoolean mMatchOverFlag = new AtomicBoolean();
    private final Object mMutex = new Object();
    private final TTTStateBuilder mTTTStateBuilder;
    private boolean mGameEnded;

    public GameManager(ServerManager serverManager, Client clientOne, Client clientTwo) {
        assert serverManager != null;
        assert clientOne != null;
        assert clientTwo != null;

        mServerManager = serverManager;
        mClientOne = clientOne;
        mClientTwo = clientTwo;
        mClientWithTurn = mClientOne;
        mClientRequests = new ConcurrentHashMap<Client, Queue<GameMove>>(2);
        mClientRequests.put(mClientOne, new ConcurrentLinkedQueue<GameMove>());
        mClientRequests.put(mClientTwo, new ConcurrentLinkedQueue<GameMove>());
        mMatchOverFlag.set(false);
        mGameState = new GameState(mClientWithTurn.getPlayerName() + " has the turn.", new TTTState());
        mTTTStateBuilder = new TTTStateBuilder(mGameState.getTTTState());
        mGameEnded = false;
    }

    @Override
    public void requestMove(Client client, GameMove move) {
        assert client != null;
        assert move != null;

        final Queue<GameMove> movesList = mClientRequests.get(client);
        movesList.add(move);
    }

    @Override
    public void requestRematch(Client client) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendHeartbeat(Client client) {
        // TODO Auto-generated method stub

    }

    public void processGameMoveRequests() {
        // TODO: Add caching of previous states
        while (true) {
            synchronized (mMutex) {
                if (mMatchOverFlag.get())
                    break;

                if (mGameEnded) 
                    continue;

                if (mClientRequests.get(mClientWithTurn).size() < 1)
                    continue;

                System.out.println("Processing client request in Game Manager.");
                final GameMove gameMove = mClientRequests.get(mClientWithTurn).poll();

                if (!RuleChecker.isMoveValid(gameMove, mGameState.getTTTState())) {
                    continue;
                }

                final Piece newPiece;
                if (mClientWithTurn == mClientOne) {
                    newPiece = new XPiece(gameMove.getTargetXPosition(), gameMove.getTargetYPosition());
                } else {
                    newPiece = new OPiece(gameMove.getTargetXPosition(), gameMove.getTargetYPosition());
                }

                final TTTState newTttState = mTTTStateBuilder.addPiece(newPiece).build();
                final Client clientNotWithTurn = (mClientWithTurn == mClientOne) ? mClientTwo : mClientOne;

                if (RuleChecker.isGameOver(newTttState)) {
                    mGameState = new GameState(mClientWithTurn.getPlayerName() + " won. Game over.", newTttState);
                    mClientWithTurn.endGame(mGameState);
                    clientNotWithTurn.endGame(mGameState);
                    mGameEnded = true;
                } else {
                    mGameState = new GameState(clientNotWithTurn.getPlayerName() + " has the turn.", newTttState);
                    mClientWithTurn.updateGame(mGameState);
                    clientNotWithTurn.updateGame(mGameState);
                    mClientWithTurn = clientNotWithTurn;
                }
            }
        }
    }

    public void processRematchRequests() {
        while (true) {
            synchronized (mMutex) {

            }
        }
    }

    public void processEndMatchRequests() {
        while (true) {
            synchronized (mMutex) {
                
            }
        }

    }

    public void endMatch() {
        mMatchOverFlag.set(true);
    }

    public GameState getGameState() {
        synchronized (mMutex) {
            return mGameState;
        }
    }

}