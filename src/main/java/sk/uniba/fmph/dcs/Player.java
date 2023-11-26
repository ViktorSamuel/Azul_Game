package sk.uniba.fmph.dcs;

public class Player {
    private final int id;
    private String name;

    public Player(int id) {
        this.id = id;
        this.name = "Player " + id;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

}
