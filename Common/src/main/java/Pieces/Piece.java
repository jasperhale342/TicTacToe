package Pieces;

public abstract class Piece {
    private int xPosition;
    private int yPosition;

    abstract public PieceType getPieceType();

    public void setPosition(int xPosition, int yPosition){
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