package Game.Utility;

import API.GameMove;
import Pieces.Piece;
import State.TTTState;

import java.util.List;

public class RuleChecker {

    public static boolean isMoveValid(GameMove gameMove, TTTState tttState) {
        assert gameMove != null;
        assert tttState != null;
        
        final Piece pieceAtTarget = tttState.getPiece(gameMove.getTargetXPosition(), gameMove.getTargetYPosition());
        return (pieceAtTarget == null);
    }

    public static boolean isTied(TTTState tttState) {
        assert tttState != null;
        return !isGameOver(tttState) && tttState.getAllPieces().size() == 9;
    }

    //how to win
    //1,1 1,2 1,3
    //2,1 2,2 2,3
    //3,1 3,2 3,3
    //1,1 2,1 3,1
    //1,2 2,2 3,2
    //1,3 2,3 3,3
    //1,1 2,2 3,3
    //3,1 2,2 1,3
    public static boolean isGameOver(TTTState tttState) {
        assert tttState != null;
        List<Piece> pieceList = tttState.getAllPieces();
        if (pieceList.size() < 5) {
            return false;
        }
        if (checkCombination(tttState.getPiece(1,1), tttState.getPiece(1,2), tttState.getPiece(1,3))) {
            return true;
        }
        if (checkCombination(tttState.getPiece(2,1), tttState.getPiece(2,2), tttState.getPiece(2,3))) {
            return true;
        }
        if (checkCombination(tttState.getPiece(3,1), tttState.getPiece(3,2), tttState.getPiece(3,3))) {
            return true;
        }
        if (checkCombination(tttState.getPiece(1,1), tttState.getPiece(2,1), tttState.getPiece(3,1))) {
            return true;
        }
        if (checkCombination(tttState.getPiece(1,2), tttState.getPiece(2,2), tttState.getPiece(3,2))) {
            return true;
        }
        if (checkCombination(tttState.getPiece(1,3), tttState.getPiece(2,3), tttState.getPiece(3,3))) {
            return true;
        }
        if (checkCombination(tttState.getPiece(1,1), tttState.getPiece(2,2), tttState.getPiece(3,3))) {
            return true;
        }
        if (checkCombination(tttState.getPiece(3,1), tttState.getPiece(2,2), tttState.getPiece(1,3))) {
            return true;
        }
        return false;
    }

    private static boolean checkCombination(Piece piece1, Piece piece2, Piece piece3) {
        if (piece1 != null && piece2 != null && piece3 != null) {
            return piece1.getPieceType() == piece2.getPieceType() && piece1.getPieceType() == piece3.getPieceType();
        }
        return false;
    }

}