package Pages.WaitPage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WaitPageController {
    private WaitPageModel model;

    @FXML
    private Label waitMessage;

    public void setWaitMessage(String waitMessage) {
        model.updateErrorMessage(waitMessage);
    }

    public void setModel(WaitPageModel model) {
        assert model != null;
        this.model = model;
    }

    public void updateView() {
        this.waitMessage.setText(model.getErrorMessage());
    }
}