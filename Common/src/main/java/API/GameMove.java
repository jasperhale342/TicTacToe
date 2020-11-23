package API;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class GameMove implements Remote {

    public GameMove(int targetX, int targetY) {
        assert targetX > 0;
        assert targetY > 0;
        mTargetX = targetX;
        mTargetY = targetY;
    }

    private final int mTargetX;
    private final int mTargetY;

    public int getTargetXPosition() throws RemoteException {
        return mTargetX;
    }

    public int getTargetYPosition() throws RemoteException {
        return mTargetY;
    }

}