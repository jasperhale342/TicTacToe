package State;

import Pieces.Piece;
import java.util.List;

/**
 * TTTState represents the pieces and their locations on the TTTBoard currently.
 */
public class TTTState {
    private final List<Piece> pieces;

    /** Initializes the TTTState with the pieces and their locations in the state.
     *
     * @param pieces - the Pieces currently on the board. The index in the array specifies the location.
     *               The mapping of the index to location on the board can be seen below where y = 1 or 2 or 3
     *               if (x=1) -> Pieces.get(y)
     *               if (x=2) -> Pieces.get(y+3)
     *               if (x=3) -> Pieces.get(y+6)
     *               The first index (0) is always null. The rest of the items in the list are null if they do
     *               not yet have a piece at that location.
     */
    public TTTState(List<Piece> pieces) throws IllegalArgumentException {
        if (pieces.size() > 10) {
            throw new IllegalArgumentException("The list must not contain more than 10 pieces.");
        }
        this.pieces = pieces;
    }

    /** Returns the piece at a given location. If no piece is found, it returns null.
     *
     * @param x - the x coordinate position on the board.
     * @param y - the y coordinate position on the board.
     * @return Piece at location or null if no piece at that location.
     * Throws an IllegalArgumentException if the x or y position is invalid.
     */
    public Piece getPiece(int x, int y) throws IllegalArgumentException {
        if (x < 1 || x > 3) {
            throw new IllegalArgumentException("The X Position should be between 1 and 3.");
        }
        if (y < 1 || y > 3) {
            throw new IllegalArgumentException("The Y Position should be between 1 and 3.");
        }
        switch(x) {
            case 1:
                return pieces.get(y);
            case 2:
                return pieces.get(y+3);
            case 3:
                return pieces.get(y+6);
            default:
                return null;
        }
    }

    /** Returns all pieces in the current TTTState.
     *
     * @return List holding all pieces.
     */
    public List<Piece> getAllPieces() {
        return pieces;
    }
}