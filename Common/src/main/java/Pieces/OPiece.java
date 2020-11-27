package Pieces;

public class OPiece extends Piece{
    private PieceType piece; 

    public OPiece(int xPosition, int yPosition){
        super.setPosition(xPosition, yPosition);
        this.piece = PieceType.TYPE_O;
    }

    public PieceType getPieceType(){
        return this.piece;
    }
    
}