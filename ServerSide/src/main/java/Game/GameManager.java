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
    private volatile boolean mGameEnded;
    private volatile boolean mClientOneRematchRequested;
    private volatile boolean mClientTwoRematchRequested;

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
        mClientOneRematchRequested = false;
        mClientTwoRematchRequested = false;
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
        if (!mGameEnded)
            return;
        if (client == mClientOne) {
            mClientOneRematchRequested = true;
        } else if (client == mClientTwo) {
            mClientTwoRematchRequested = true;
        }
    }

    @Override
    public void sendHeartbeat(Client client) {
        // TODO Auto-generated method stub

    }

    public void processGameMoveRequests() {
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

                assert gameMove != null;
                if (!RuleChecker.isMoveValid(gameMove, mGameState.getTTTState())) {
                    mClientWithTurn.displayErrror("Not a valid move!");
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

                if (RuleChecker.isGameOver(newTttState) || RuleChecker.isTied(newTttState)) {
                    final String message = (RuleChecker.isGameOver(newTttState))
                            ? mClientWithTurn.getPlayerName() + " won. Game over."
                            : "The game is tied. Game over.";
                    mGameState = new GameState(message, newTttState);
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
            if (mMatchOverFlag.get())
                break;
            if (!mGameEnded)
                continue;
            if (!mClientOneRematchRequested || !mClientTwoRematchRequested)
                continue;

            synchronized (mMutex) {
                mGameEnded = false;
                mClientWithTurn = mClientOne;
                mClientRequests.clear();
                mClientRequests.put(mClientOne, new ConcurrentLinkedQueue<GameMove>());
                mClientRequests.put(mClientTwo, new ConcurrentLinkedQueue<GameMove>());
                mGameState = new GameState(mClientWithTurn.getPlayerName() + " has the turn.", new TTTState());
                mTTTStateBuilder.setNewState(mGameState.getTTTState());
                mClientOneRematchRequested = false;
                mClientTwoRematchRequested = false;
                mClientOne.startGame(this, mGameState);
                mClientTwo.startGame(this, mGameState);
            }
        }
    }

    public void processEndMatchRequest() {
        synchronized (mMutex) {
            final TTTState tttState = mGameState.getTTTState();
            mGameState = new GameState("Game over due to a player leaving. No rematch.", tttState);
        }
    }

    public void endMatch() {
        final boolean oldValue = mMatchOverFlag.getAndSet(true);
        if (!oldValue && !mGameEnded)
            processEndMatchRequest();
    }

    public GameState getGameState() {
        synchronized (mMutex) {
            return mGameState;
        }
    }

    public Client getClientOne() {
        return mClientOne;
    }

    public Client getClientTwo() {
        return mClientTwo;
    }
}