package sk.uniba.fmph.dcs;
import java.util.List;
public interface BagInterface {
    public Tile[]take(int count);
    public String state();
    public void fill(List<Tile> newTiles);
    public void fillWithoutShuffle(List<Tile> newTiles);
    public boolean isEmpty();
}
