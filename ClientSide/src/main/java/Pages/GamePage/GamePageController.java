package Pages.GamePage;

import Client.GameListener;
import Pieces.Piece;
import Pieces.PieceType;
import State.GameState;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.List;


public class GamePageController{
    private GamePageModel model;
    private GameListener gameListener;

    @FXML
    private Label errorMessage;

    @FXML
    private GridPane grid;

    public GamePageController(GamePageModel gamePageModel){
        assert gamePageModel != null;
        this.model = gamePageModel;
    }

    @FXML
    public void sendMove(MouseEvent event){
        if (!model.getErrorMessage().equals("")) {
            errorMessage.setText("");
        }
        if (!model.getCurrentGameState().getTurn().equals(model.getPlayerName()+ " has the turn.")) {
            model.updateErrorMessage("Not your turn!");
            displayErrorMessage();
            return;
        }
        Integer x = GridPane.getColumnIndex((Node) event.getSource());
        Integer y = GridPane.getRowIndex((Node) event.getSource());
        if (x == null || y == null) {
            throw new NullPointerException("GridPane does not have button at that location.");
        }
        gameListener.onMoveRequest(x+1, y+1);
    }

    public void updateView() throws NullPointerException {
        model.updateErrorMessage("");
        displayErrorMessage();
        GameState currentGameState = model.getCurrentGameState();
        List<Piece> pieces = currentGameState.getTTTState().getAllPieces();
        assert pieces != null;
        if (currentGameState.getTurn().contains("won") || currentGameState.getTurn().contains("tied") || currentGameState.getTurn().contains("leaving")) {
            errorMessage.setText(currentGameState.getTurn());
        }
        for (Piece piece : pieces) {
            Button button = getButtonAtLocation(piece.getXPosition()-1, piece.getYPosition()-1);
            if (button == null) {
                model.updateErrorMessage("There is no button at this location, please try again.");
                displayErrorMessage();
                throw new NullPointerException("Button does not exist at this location.");
            }
            if (piece.getPieceType() == PieceType.TYPE_O) {
                button.setText("O");
            }
            if (piece.getPieceType() == PieceType.TYPE_X) {
                button.setText("X");
            }
        }
    }

    private Button getButtonAtLocation(int x, int y) {
        for (Node node : grid.getChildren()) {
            int buttonX = GridPane.getColumnIndex(node);
            int buttonY = GridPane.getRowIndex(node);
            if (buttonX == x && buttonY == y) {
                return (Button) node;
            }
        }
        return null;
    }

    public void setGameState(GameState state) {
        model.updateGameState(state);
    }

    public boolean attachMoveListener(GameListener listener) {
        if (listener != null) {
            gameListener = listener;
            return true;
        }
        return false;
    }

    public void setErrorMessage(String error) {
        model.updateErrorMessage(error);
    }

    public void displayErrorMessage() {
        errorMessage.setText(model.getErrorMessage());
    }

    @FXML
    public void leaveGame() {
        gameListener.onClientLeaveMatch();
    }
}