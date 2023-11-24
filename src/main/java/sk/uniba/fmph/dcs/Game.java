package sk.uniba.fmph.dcs;

public class Game implements GameInterface {
    private int currentPlayerId;
    private int startingPlayerId;
    private TableAreaInterface tableArea;
    private BoardInterface[] boards;

    public Game(BoardInterface[] boards, TableAreaInterface tableArea){
        this.boards = boards;
        this.tableArea = tableArea;
        currentPlayerId = startingPlayerId = 0;
    }

    @Override
    public boolean take(int playerId, int sourceId, int idx, int destinationIxd) {
        if (playerId != currentPlayerId ||
                idx < 1 || idx > 5 ||
                destinationIxd < 0 || destinationIxd > 4) {
            return false;
        }
        Tile[] tiles = tableArea.take(sourceId, idx);
        for(Tile t : tiles){
            if (t == Tile.STARTING_PLAYER) startingPlayerId = playerId;
        }
        boards[playerId].put(destinationIxd, tiles);
        currentPlayerId = (currentPlayerId + 1) % boards.length;

        if (tableArea.isRoundEnd()){
           boolean endGame = false;
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
}
