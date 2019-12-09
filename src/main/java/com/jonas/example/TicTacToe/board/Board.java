package com.jonas.example.TicTacToe.board;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private static final int DEFAULT_ROW_SIZE = 3;
    private static final int DEFAULT_COLUMN_SIZE = 3;
    private static final int DEFAULT_PLAYER_COUNT = 2;

    private enum ScanDirection{
        ROW,COLUMN,DIAGNAL_LEFT,DIAGNAL_RIGHT;
    }

    private final int rowSize;
    private final int columnSize;
    private final Integer[][] gameBoard;
    private int playerCount;
    private int currentPlayer;
    private String winningPlayer;
    private Move winningMove;


    private final Set<Move> completedMoves;
    private String name;


    public Board() {
        completedMoves = new HashSet<Move>();
        currentPlayer = 1;
        winningPlayer = null;
        rowSize = DEFAULT_ROW_SIZE;
        columnSize = DEFAULT_COLUMN_SIZE;
        playerCount = DEFAULT_PLAYER_COUNT;
        gameBoard = new Integer[rowSize][columnSize];
    }


    public Board(int players) {
        completedMoves = new HashSet<Move>();
        gameBoard = new Integer[3][3];
        currentPlayer = 1;
        winningPlayer = null;
        rowSize = DEFAULT_ROW_SIZE;
        columnSize = DEFAULT_COLUMN_SIZE;
        playerCount = players;
    }

    public Board(int players, int boardSize) {
        completedMoves = new HashSet<Move>();
        currentPlayer = 1;
        winningPlayer = null;
        playerCount = players;
        this.rowSize = boardSize;
        this.columnSize = boardSize;
        gameBoard = new Integer[columnSize][rowSize];
    }

    public Board(int players, int rowSize, int columnSize) {
        completedMoves = new HashSet<Move>();
        currentPlayer = 1;
        winningPlayer = null;
        playerCount = players;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        gameBoard = new Integer[columnSize][rowSize];
    }

    //passing all the moves from the moves collection list from class TicTacToe
    //You create a new pass
    public boolean play(int row, int column){
        return play(new Move(row, column,currentPlayer));
    }

    public boolean play(Move move) {
        if (!validMove(move)) {
            // always fail early
            throw new IllegalStateException("player "+ getCurrentPlayer() + " can't make this move at "+ move.getRow() +"," + move.getColumn());
        }

        doMove(move);
        boolean playerWon = isWinningMove(move);

        if(playerWon){
            winningPlayer = "Player " + move.getPlayer();
            winningMove = move;
            return true;

        }

        shiftPlayer();

        boolean outOfMoves = movesLeft() <= 0;



        return outOfMoves;
    }

    public void shiftPlayer(){

        if(currentPlayer == getPlayerCount()){
            currentPlayer = 1;
        }
        else {
            currentPlayer++;

        }
    }

    private boolean validMove(Move move) {

        if(completedMoves.contains(move)){
            return false;
        }

        boolean noMoveAtIndex = false;
        boolean indexesAreOk = move.getRow() >= 0 || move.getRow() < getRowSize();
        indexesAreOk = indexesAreOk && move.getColumn() >= 0 || move.getColumn() < getColumnSize();
        if (indexesAreOk) {
            noMoveAtIndex = getMoveAt(move.getRow(), move.getColumn()) == null;
        }
        return indexesAreOk && noMoveAtIndex;
    }


    public Integer getMoveAt(int row, int column) {
        return gameBoard[row][column];
    }


    private void doMove(Move move){
        Integer player = move.getPlayer();
        if(player == null){
            player = getCurrentPlayer();
        }

        gameBoard[move.getRow()][move.getColumn()] = player;


    }


    public int getPlayerCount() {
        return playerCount;
    }

    public boolean isWinningMove(Move move){
        return winsOnColumn(move)|| winsOnRow(move) || winsOnDiagnal(move);

    }

    private boolean winsOnDiagnal(Move move){
        boolean left = scan(move, ScanDirection.DIAGNAL_LEFT);
        boolean right = scan(move,ScanDirection.DIAGNAL_RIGHT);
        return left || right;
    }

    private boolean winsOnColumn(Move move){
        return scan(move, ScanDirection.COLUMN);
    }

    private boolean winsOnRow(Move move){
        return scan(move,ScanDirection.ROW);
    }

    private boolean scan(Move move, ScanDirection scanDirection){
        int wall = 0;
        int direction = 0;

        int player = move.getPlayer();

        if(scanDirection == ScanDirection.ROW || scanDirection == ScanDirection.DIAGNAL_RIGHT){
            //just put the move.getColumn()
            // wall is a fix number of rows we wil check
            direction = move.getColumn(); // move column 0 --> 2
            wall = getRowSize() - 1; //always two row or column doesn't matter

        }

        if(scanDirection == ScanDirection.COLUMN || scanDirection == ScanDirection.DIAGNAL_LEFT){

            direction = move.getRow(); // move row 0 --> 2
            wall = getColumnSize() -1; //2
        }

        int start = getStart(direction);
        //wall fix number
        //direction the number been check
        int end = getEnd(wall, direction);

        int markCount = 0;

        for(int i = start; i <= end; i++){

            if(markCount == 3){
                return true;
            }
            Integer playerId = null;
            if(scanDirection == ScanDirection.ROW){
                //checking all columns in the row
                playerId = getMoveAt(move.getRow(),i);
            }
            if(scanDirection == ScanDirection.COLUMN){
                playerId = getMoveAt(i,move.getColumn());

            }
            if(scanDirection == ScanDirection.DIAGNAL_LEFT){
                int row = i;
                int column = end - i;

                playerId = getMoveAt(row,column);

            }

            if(scanDirection == ScanDirection.DIAGNAL_RIGHT){
                //0 --> 2 - 2
                int row = getStart((move.getRow() - end)) + i;
                int column = i;

                playerId = getMoveAt(row,column);
            }

            if(playerId != null && playerId == player){
                markCount++;
            }
            else{
                markCount = 0;
            }

        }

        return markCount ==3;
    }

    public String whoWon(){

        return winningPlayer;
    }


    public int getStart(int direction){
        return Math.min(
                Math.max(0,direction -1),
                Math.max(0,direction -2)
        );

    }

    private int getEnd(int wall, int midPoint){
        return Math.max(
                Math.min(wall, midPoint + 2),
                (Math.min(wall, midPoint + 1)
                ));
    }

    /**
     *
     * @return the amount of empty spaces remaining on the game board, or if theres a winning player, zero.
     */
    public int movesLeft(){

        if(whoWon() != null){
            return 0;
        }

        int moveCount = 0;
        for(int x = 0; x < getRowSize(); x++){
            for(int y = 0; y <getColumnSize();y++){
                System.out.println(x +"," + y  );
                System.out.println(getMoveAt(x,y) == null ? 1 : 0 );

                moveCount += getMoveAt(x,y) == null ? 1 : 0;
            }
        }
        System.out.println(moveCount + " moveleft");
        return moveCount;
    }
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public Move getWinningMove(){
        return winningMove;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }


    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }



    public int getRowSize() {
        return rowSize;
    }


    public int getColumnSize() {
        return columnSize;
    }
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for(int row = 0; row < getRowSize(); row++) {
            for(int column = 0; column < getColumnSize(); column++) {
                Integer move = getMoveAt(row, column);
                String
                        moveToPrint = "";
                if (move == null) {
                    moveToPrint = " ";
                } else {
                    moveToPrint = move.toString();
                }
                stringBuffer.append("|").append(moveToPrint);
            }
            stringBuffer.append("|\n");
        }
        return stringBuffer.toString();
    }

}
