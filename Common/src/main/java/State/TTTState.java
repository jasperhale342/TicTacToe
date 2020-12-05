package State;

import Pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TTTState represents the pieces and their locations on the TTTBoard currently.
 */
public class TTTState {

    private static int convert2Dto1D(int xPos, int yPos) {
        assert xPos > 1 && xPos < 4;
        assert yPos > 1 && yPos < 4;
        switch (xPos) {
            case 1:
                return yPos - 1;
            case 2:
                return yPos + 2;
            case 3:
                return yPos + 5;
        }
        return -1;
    }

    private final Piece[] mPieces = new Piece[9];

    /**
     * Initializes the TTTState with some set of pieces already on the board.
     *
     * @param pieces - the Pieces currently on the board. There needs to be no
     *               mapping between an element's position in the list and its
     *               positiion on the board as each element contains its own board
     *               position information. It is however required that the list of
     *               pieces contain no duplicates and that there not be more pieces
     *               than are allowed on a board.
     */
    public TTTState(List<Piece> pieces) {
        if (pieces.size() > 9) {
            throw new IllegalArgumentException("The list must not contain more than 9 pieces.");
        }
        for (Piece piece : pieces) {
            final int index = convert2Dto1D(piece.getXPosition(), piece.getYPosition());
            assert index > -1; // the coordinate information for each piece should be valid
            assert mPieces[index] == null; // there should be no duplicates in the list
            mPieces[index] = piece;
        }
    }

    /**
     * Initializes the {@code TTTState} with no pieces.
     */
    public TTTState() {

    }

    /**
     * Returns the piece at a given location. If no piece is found, it returns null.
     *
     * @param x - the x coordinate position on the board.
     * @param y - the y coordinate position on the board.
     * @return Piece at location or null if no piece at that location.
     */
    public Piece getPiece(int x, int y) {
        if (x < 1 || x > 3) {
            throw new IllegalArgumentException("The X Position should be between 1 and 3.");
        }
        if (y < 1 || y > 3) {
            throw new IllegalArgumentException("The Y Position should be between 1 and 3.");
        }
        switch (x) {
            case 1:
                return mPieces[y - 1];
            case 2:
                return mPieces[y + 2];
            case 3:
                return mPieces[y + 5];
            default:
                return null;
        }
    }

    /**
     * Returns all pieces currently on the board.
     *
     * @return List holding all pieces.
     */
    public List<Piece> getAllPieces() {
        return new ArrayList<Piece>(Arrays.asList(mPieces));
    }
}