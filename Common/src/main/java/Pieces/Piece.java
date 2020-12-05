package Pieces;

public abstract class Piece {
    private final int xPosition;
    private final int yPosition;

    abstract public PieceType getPieceType();

    public Piece(int xPosition, int yPosition) {
        assert xPosition >= 1 && xPosition <=3;
        assert yPosition >= 1 && yPosition <=3;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    
    public int getXPosition(){
        return this.xPosition;
    }
    
    public int getYPosition(){
        return this.yPosition;
    }
}