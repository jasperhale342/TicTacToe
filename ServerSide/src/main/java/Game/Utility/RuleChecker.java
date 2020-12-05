package Game.Utility;

import API.GameMove;
import Pieces.Piece;
import State.TTTState;

public class RuleChecker {

    public static boolean isMoveValid(GameMove gameMove, TTTState tttState) {
        assert gameMove != null;
        assert tttState != null;
        
        final Piece pieceAtTarget = tttState.getPiece(gameMove.getTargetXPosition(), gameMove.getTargetYPosition());
        return (pieceAtTarget == null);
    }

    public static boolean isGameOver(TTTState tttState) {
        assert tttState != null;
        
        // TODO: Implement this method
        return false;
    }

}