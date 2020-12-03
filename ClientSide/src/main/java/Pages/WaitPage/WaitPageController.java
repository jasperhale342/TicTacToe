package Pages.WaitPage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WaitPageController {
    private WaitPageModel model;

    @FXML
    private Label waitMessage;

    public void setWaitMessage(String waitMessage) {
        this.waitMessage.setText(waitMessage);
    }

    public void setModel(WaitPageModel model) {
        assert model != null;
        this.model = model;
    }
}