package Game.Utility;

import java.util.List;

import Pieces.Piece;
import State.TTTState;

public class TTTStateBuilder {

    private TTTState mCurrentTttState;

    public TTTStateBuilder(TTTState tttState) {
        assert tttState != null;
        mCurrentTttState = tttState;
    }

    public TTTStateBuilder addPiece(Piece piece) {
        final List<Piece> piecesList = mCurrentTttState.getAllPieces();
        piecesList.add(piece);
        mCurrentTttState = new TTTState(piecesList);
        return this;
    }

    public TTTStateBuilder setNewState(TTTState tttState) {
        assert tttState != null;
        mCurrentTttState = tttState;
        return this;
    }

    public TTTState build() {
        return mCurrentTttState;
    }

}