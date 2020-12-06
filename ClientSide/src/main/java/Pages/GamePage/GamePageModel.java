package Pages.GamePage;

import State.GameState;

public class GamePageModel {
    private GameState currentGameState;
    private String errorMessage = "";
    private final String playerName;


    public GamePageModel(String name, GameState initialState){
        assert name != null;
        assert initialState != null;
        playerName = name;
        currentGameState = initialState;
    }

    public void updateGameState(GameState gameState) {
        assert gameState != null;
        currentGameState = gameState;
    }

    public void updateErrorMessage(String errorMessage) {
        assert errorMessage != null;
        this.errorMessage = errorMessage;
    }

    protected GameState getCurrentGameState() {
        return currentGameState;
    }

    protected String getPlayerName() {
        return playerName;
    }

    protected String getErrorMessage() {
        return errorMessage;
    }

}