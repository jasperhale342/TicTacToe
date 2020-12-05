package Client;

import java.io.IOException;

import API.Client;
import API.GameServer;
import API.MatchmakerServer;
import Pages.StartPage.StartPageController;
import Pages.StartPage.StartPageModel;
import Pages.WaitPage.WaitPageController;
import State.GameState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

public class ClientManager implements Client {

    private MatchmakerServer mMatchmakerServer;
    private GameServer mGameServer;
    private String mPlayerName;
    private ClientState mClientState;

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
            StartPageController controller = new StartPageController(this, new StartPageModel());
            loader.setController(controller);
            Parent root = loader.load();
            openWindow(root, new Stage(), "Start Game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void waitForGame(String waitMessage) {
        if (mClientState != ClientState.STARTING) {
            return;
        }
        Platform.runLater(() -> {
            spawnWaitPage(waitMessage);
        });
        mClientState = ClientState.WAITING;
    }

    @Override
    public void startGame(GameServer gameServer, GameState initialState) {
        // TODO Auto-generated method stub
        if (mClientState != ClientState.WAITING) {
            return;
        }
        mClientState = ClientState.GAMING;
        System.out.println("Game is starting");
    }

    @Override
    public void updateGame(GameState newState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void endGame(GameState endState) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getPlayerName() {
        return mPlayerName;
    }

    public void sendFindGameRequest(String playerName) {
        mPlayerName = playerName;
        mMatchmakerServer.requestNewGame(this);
    }

    private void spawnWaitPage(String waitMessage) {
        System.out.println("Client will wait for game");
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("WaitPageView.fxml"));
            Parent root = loader.load();
            WaitPageController controller = loader.getController();
            controller.setWaitMessage(waitMessage);
            openWindow(root, new Stage(), "Wait for Game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openWindow(Parent root, Stage stage, String title) {
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
}