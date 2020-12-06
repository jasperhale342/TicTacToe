package Client;

import java.io.IOException;

import API.Client;
import API.GameMove;
import API.GameServer;
import API.MatchmakerServer;
import Pages.GamePage.GamePageController;
import Pages.GamePage.GamePageModel;
import Pages.EndPage.EndPageController;
import Pages.EndPage.EndPageModel;
import Pages.StartPage.StartPageController;
import Pages.StartPage.StartPageModel;
import Pages.WaitPage.WaitPageController;
import Pages.WaitPage.WaitPageModel;
import State.GameState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Platform;

public class ClientManager implements Client, GameListener {

    private MatchmakerServer mMatchmakerServer;
    private GameServer mGameServer;
    private String mPlayerName;
    private ClientState mClientState;
    private StartPageController startPageController;
    private WaitPageController waitPageController;
    private GamePageController gamePageController;
    private EndPageController endPageController;
    private Stage currentStage;

    public ClientManager(MatchmakerServer matchmakerServer) {
        assert matchmakerServer != null;
        mMatchmakerServer = matchmakerServer;
        mClientState = ClientState.STARTING;
    }

    public void init() {
        if (mClientState != ClientState.STARTING) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("StartPageView.fxml"));
            buildStartPageController();
            loader.setController(startPageController);
            Parent root = loader.load();
            currentStage = new Stage();
            openWindow(root, currentStage, "Start Game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void waitForGame(String waitMessage) {
        if (mClientState != ClientState.STARTING) {
            return;
        }
        Platform.runLater(() -> spawnWaitPage(waitMessage));
        mClientState = ClientState.WAITING;
    }

    @Override
    public void startGame(GameServer gameServer, GameState initialState) {
        assert gameServer != null;
        assert initialState != null;
        mGameServer = gameServer;
        if (mClientState != ClientState.WAITING && mClientState != ClientState.REMATCH) {
            return;
        }
        Platform.runLater(() -> spawnGamePage(initialState));
        mClientState = ClientState.GAMING;
    }

    @Override
    public void updateGame(GameState newState) {
        if (mClientState != ClientState.GAMING) {
            return;
        }
        Platform.runLater(() -> {
            gamePageController.setGameState(newState);
            gamePageController.updateView();
        });
    }

    @Override
    public void endGame(GameState endState) {
        if (mClientState != ClientState.GAMING) {
            return;
        }
        Platform.runLater(() -> {
            spawnEndPage(endState);
        });
        mClientState = ClientState.DONE;
    }

    @Override
    public String getPlayerName() {
        return mPlayerName;
    }

    @Override
    public void displayErrror(String error) {
        switch (mClientState) {
            case STARTING:
                Platform.runLater(() -> {
                    startPageController.setErrorMessage(error);
                    startPageController.displayError();
                });
                break;
            case WAITING:
                Platform.runLater(() -> {
                    waitPageController.setWaitMessage(error);
                    waitPageController.updateView();
                });
                break;
            case GAMING:
                Platform.runLater(() -> {
                    gamePageController.setErrorMessage(error);
                    gamePageController.displayErrorMessage();
                });
                break;
            case DONE:
                Platform.runLater(() -> {
                    endPageController.setErrorMessage(error);
                    endPageController.displayErrorMessage();
                });
                break;
            default:
                throw new NullPointerException("No existing client state!");
        }
    }
    
    public void sendFindGameRequest(String playerName) {
        mPlayerName = playerName;
        mMatchmakerServer.requestNewGame(this);
    }

    public void sendMoveRequest(int x, int y) {
        mGameServer.requestMove(this, new GameMove(x, y));
    }

    public void sendRematchRequest() {
        mGameServer.requestRematch(this);
        mClientState = ClientState.REMATCH;
    }

    @Override
    public void onFindGameRequest(String playerName) {
        sendFindGameRequest(playerName);
    }

    @Override
    public void onMoveRequest(int x, int y) {
        sendMoveRequest(x, y);
    }

    @Override
    public void onRequestRematch() {
        sendRematchRequest();
    }

    @Override
    public void onClientLeaveMatch() {
        mClientState = ClientState.STARTING;
        mMatchmakerServer.endMatch(this);
        Platform.runLater(() -> {
            spawnStartPage("You left the game!");
        });
    }

    @Override
    public void onClientClose() {
        if (mClientState == ClientState.GAMING || mClientState == ClientState.DONE) {
            mMatchmakerServer.endMatch(this);
        }
    }

    private void spawnWaitPage(String waitMessage) {
        System.out.println("Client will wait for game");
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("WaitPageView.fxml"));
            Parent root = loader.load();
            waitPageController = loader.getController();
            waitPageController.setModel(new WaitPageModel());
            waitPageController.setWaitMessage(waitMessage);
            waitPageController.updateView();
            currentStage = new Stage();
            openWindowHideClose(root, currentStage, getPlayerName() + ": Wait for Game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void spawnGamePage(GameState initialState) {
        System.out.println("Game is starting");
        currentStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("GamePageView.fxml"));
            buildGamePageController(initialState);
            loader.setController(gamePageController);
            Parent root = loader.load();
            currentStage = new Stage();
            openWindow(root, currentStage, getPlayerName() + ": Play Tic Tac Toe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void spawnStartPage(String message) {
        System.out.println("StartPage is starting");
        currentStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("StartPageView.fxml"));
            buildStartPageController();
            loader.setController(startPageController);
            startPageController.setErrorMessage(message);
            Parent root = loader.load();
            startPageController.displayError();

            currentStage = new Stage();
            openWindow(root, currentStage, getPlayerName() + ": Play Tic Tac Toe");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void spawnEndPage(GameState endState) {
        System.out.println("Gameover");
        currentStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("EndPageView.fxml"));
            buildEndPageController(endState);
            loader.setController(endPageController);

            Parent root = loader.load();
            endPageController.updateView();
            currentStage = new Stage();
            openWindow(root, currentStage, getPlayerName() + ": Gameover");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openWindow(Parent root, Stage stage, String title) {
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.setOnCloseRequest((windowEvent) -> {onClientClose();});
        stage.show();
    }

    private void openWindowHideClose(Parent root, Stage stage, String title) {
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(title);
        stage.show();
    }

    private void buildStartPageController() {
        startPageController = new StartPageController(new StartPageModel());
        boolean result = startPageController.attachFindGameListener(this);
        if (!result) {
            throw new NullPointerException("Listener not correctly attached");
        }
    }

    private void buildGamePageController(GameState initialState) {
        gamePageController = new GamePageController(new GamePageModel(getPlayerName(), initialState));
        boolean result = gamePageController.attachMoveListener(this);
        if (!result) {
            throw new NullPointerException("Listener not correctly attached");
        }
    }

    private void buildEndPageController(GameState endState) {
        endPageController = new EndPageController(new EndPageModel(getPlayerName(), endState));
        boolean result = endPageController.attachRematchListener(this);
        if (!result) {
            throw new NullPointerException("Listener not correctly attached");
        }
    }
}