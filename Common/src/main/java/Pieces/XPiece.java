package Pieces;

public class XPiece extends Piece {
    private final PieceType piece; 

    public XPiece(int xPosition, int yPosition){
        super(xPosition, yPosition);
        this.piece = PieceType.TYPE_X;
    }
    public PieceType getPieceType(){
        return this.piece;
    }
}