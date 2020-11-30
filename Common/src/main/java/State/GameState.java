package State;

/**
 * GameState represents the player's turn and the current state of the GameBoard.
 */
public class GameState {
    private final String turnText;
    private final TTTState tttState;

    /** Initializes the current GameState with the player's name (whose turn it is) and the
     * current state of the board (TTTState).
     *
     * @param turnText - Player whose turn it is.
     * @param tttState - current TTTState (including pieces and their locations).
     */
    public GameState(String turnText, TTTState tttState) {
        this.turnText = turnText;
        this.tttState = tttState;
    }

    /** Returns the name of the player whose turn it was.
     *
     * @return - the name of the player whose turn it was.
     */
    public String getTurn() {
        return turnText;
    }

    /** Returns the current state of the board (pieces and their locations)
     *
     * @return - current TTTState
     */
    public TTTState getTTTState() {
        return tttState;
    }
}