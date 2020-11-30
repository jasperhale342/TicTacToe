package API;

public interface GameServer {

    public void requestMove(Client client, GameMove move);

    public void requestRematch(Client client);

    public void sendHeartbeat(Client client);

}