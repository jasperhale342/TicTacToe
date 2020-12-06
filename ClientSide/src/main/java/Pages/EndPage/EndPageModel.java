package Pages.EndPage;
import State.GameState;

public class EndPageModel{
    private GameState endState;
    private String errorMessage;
    private final String playerName;

    public EndPageModel(String name, GameState endState){
        assert name != null;
        assert endState !=null;
        playerName = name;
        this.endState = endState;
    }

    public void updateErrorMessage(String errorMessage) {
        assert errorMessage != null;
        this.errorMessage = errorMessage;
    }

    protected GameState getCurrentGameState() {
        return endState;
    }

    protected String getPlayerName() {
        return playerName;
    }

    protected String getErrorMessage() {
        return errorMessage;
    }
}