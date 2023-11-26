package sk.uniba.fmph.dcs;

public interface GameInterface {
    boolean take(int playerId, int sourceId, int idx, int destinationIxd);
    boolean isGameOver();
    int getCurrentPlayerId();
    void setGameObserver(ObserverInterface newGameObserver);
    void removeGameObserver(ObserverInterface gameObserver);
}
