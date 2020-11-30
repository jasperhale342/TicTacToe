package API;

public class GameMove {

    public GameMove(int targetX, int targetY) {
        assert targetX > 0 && targetX < 4;
        assert targetY > 0 && targetY < 4;
        mTargetX = targetX;
        mTargetY = targetY;
    }

    private final int mTargetX;
    private final int mTargetY;

    public int getTargetXPosition() {
        return mTargetX;
    }

    public int getTargetYPosition() {
        return mTargetY;
    }

}