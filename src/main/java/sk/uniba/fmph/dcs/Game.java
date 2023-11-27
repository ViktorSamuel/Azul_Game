package sk.uniba.fmph.dcs;

public class Game implements GameInterface {
    private int currentPlayerId;
    private int startingPlayerId;
    private boolean endGame;
    private TableAreaInterface tableArea;
    private BoardInterface[] boards;
    private GameObserverInterface gameObserver;

    public Game(BoardInterface[] boards, TableAreaInterface tableArea, GameObserverInterface gameObserver){
        this.boards = boards;
        this.tableArea = tableArea;
        this.gameObserver = gameObserver;
        currentPlayerId = startingPlayerId = 0;
        endGame = false;
    }

    @Override
    public boolean take(int playerId, int sourceId, int idx, int destinationIxd) {
        if (playerId != currentPlayerId ||
                destinationIxd < 0 || destinationIxd > 4 ||
                endGame) {
            return false;
        }
        Tile[] tiles = tableArea.take(sourceId, idx);
        if (tiles.length == 0) return false;
        if (tiles[tiles.length-1] == Tile.STARTING_PLAYER) startingPlayerId = playerId;

        boards[playerId].put(destinationIxd, tiles);
        currentPlayerId = (currentPlayerId + 1) % boards.length;

        if (tableArea.isRoundEnd()){
            for (int i = 0; i < boards.length; i++){
                if(boards[i].finishRound() == FinishRoundResult.GAME_FINISHED) endGame = true;
            }

            if (endGame) {
                for (int i = 0; i < boards.length; i++){
                    boards[i].endGame();
                }
            }
            else {
                currentPlayerId = startingPlayerId;
                tableArea.startNewRound();
            }
        }
        return true;
    }
    @Override
    public boolean isGameOver(){
        return endGame;
    }
    @Override
    public int getCurrentPlayerId(){
        return currentPlayerId;
    }
    @Override
    public GameObserverInterface getGameObserver(){
        return gameObserver;
    }
}
