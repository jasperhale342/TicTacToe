package Pages.StartPage;

import Client.GameListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StartPageController {
    private StartPageModel model;
    private GameListener gameListener;

    @FXML
    private TextField nameBox;

    @FXML
    private Label errorDisplay;

    public StartPageController(StartPageModel model) {
        assert model != null;
        this.model = model;
    }

    public boolean attachFindGameListener(GameListener listener) {
        if (listener != null) {
            gameListener = listener;
            return true;
        }
        return false;
    }

    @FXML
    private void findGameButtonClicked() {
        String playerName = nameBox.getText();
        if (playerName.isEmpty()) {
            setErrorMessage("Please enter name!");
            displayError();
        } else {
            gameListener.onFindGameRequest(playerName);
            Stage stage = (Stage) nameBox.getScene().getWindow();
            stage.close();
        }
    }

    public void displayError() {
        errorDisplay.setText(model.getErrorMessage());
    }

    public void setErrorMessage(String error) {
        model.updateErrorMessage(error);
    }

}