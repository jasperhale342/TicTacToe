package Pages.StartPage;

import Client.ClientManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StartPageController {
    private StartPageModel model;
    private final ClientManager clientManager;

    @FXML
    private TextField nameBox;

    @FXML
    private Label errorDisplay;

    public StartPageController(ClientManager clientManager, StartPageModel model) {
        assert clientManager != null;
        assert model != null;
        this.clientManager = clientManager;
        this.model = model;
    }

    @FXML
    private void findGameButtonClicked() {
        String playerName = nameBox.getText();
        if (playerName.isEmpty()) {
            errorDisplay.setText("Please enter name!");
        } else {
            clientManager.sendFindGameRequest(playerName);
            Stage stage = (Stage) nameBox.getScene().getWindow();
            stage.close();
        }
    }
}