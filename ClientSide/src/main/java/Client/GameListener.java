package Client;

public interface GameListener {
    void onFindGameRequest(String playerName);
    void onMoveRequest(int x, int y);
    void onRequestRematch();
    void onClientLeaveMatch();
    void onClientClose();
}
