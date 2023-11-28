package sk.uniba.fmph.dcs;

public interface TableAreaInterface {
    public Tile[] take(int sourceId, int idx);
    public boolean isRoundEnd();
    public void startNewRound();
    public String state();
}
