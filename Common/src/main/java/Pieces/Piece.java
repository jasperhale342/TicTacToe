package Pieces;

public abstract class Piece {
    private int xPosition;
    private int yPosition;

    abstract public PieceType getPieceType();

    public void setPosition(int xPosition, int yPosition){
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