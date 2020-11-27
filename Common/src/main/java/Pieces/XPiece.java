package Pieces;

public class XPiece extends Piece{
    private PieceType piece; 

    public XPiece(int xPosition, int yPosition){
        super.setPosition(xPosition, yPosition);
        this.piece = PieceType.TYPE_X;
    }
    public PieceType getPieceType(){
        return this.piece;
    }
}