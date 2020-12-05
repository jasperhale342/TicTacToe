package API;

public interface MatchmakerServer {

    public void requestNewGame(Client client);

    public void endMatch(Client client);

}