package sk.uniba.fmph.dcs;

public interface ObserverInterface {
    void notify(String newState);
    public void notifyEverybody(String state);
}
