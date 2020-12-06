package Pieces;

public class OPiece extends Piece {
    private final PieceType piece; 

    public OPiece(int xPosition, int yPosition){
        super(xPosition, yPosition);
        this.piece = PieceType.TYPE_O;
    }

    public PieceType getPieceType(){
        return this.piece;
    }
    
}