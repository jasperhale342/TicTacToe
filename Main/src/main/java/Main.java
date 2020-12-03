import Client.ClientManager;
import Server.ServerManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private final ServerManager srvMgr;

    public Main() {
        this.srvMgr = new ServerManager();
        final Runnable requestReplyRunnable = srvMgr::respondToRequests;
        final Runnable findGamesRunnable = srvMgr::findGames;

        final Thread replyThread = new Thread(requestReplyRunnable);
        final Thread findGamesThread = new Thread(findGamesRunnable);

        replyThread.start();
        findGamesThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void createClient() {
        final ClientManager clientMgr = new ClientManager(srvMgr); //spawn a new client
        Platform.runLater(clientMgr::init);
        System.out.println("\nStarted another client.");
    }

    @FXML
    public void end(ActionEvent event) {
        System.out.println("\nExiting application. Goodbye!");
        srvMgr.cancel();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        System.exit(-1);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("OpeningScreen.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Tic Tac Toe Launcher");
        primaryStage.show();
    }
}
