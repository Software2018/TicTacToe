package com.jonas.example.TicTacToe.board;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

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

        
     
    return false;
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


	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
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

}

