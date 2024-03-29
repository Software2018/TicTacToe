package com.jonas.example.TicTacToe;

import com.jonas.example.TicTacToe.board.Board;
import com.jonas.example.TicTacToe.board.Move;

import java.util.*;



public class TicTactToe {


    private static final String TIE = "TIE";

    private static final Map<String,Integer> gamesToWinsMap = new HashMap<String,Integer>();

//	/**
//	 * accepts input in the following format:
//	 *
//	 * playerCount rowCount columnCount (sets the game with the n players, n columns, and n rows
//  * PlayerCount squareSize (defaults to a game with rows and cols the same as squareSize and the player count given)
//     * PlayerCount (defaults to a 3 by 3 game)
//     * no input (defaults to a 3 by 3 game with 2 players)	 * @param args
//	 */

    public static void main(String[] args){
        int playerCount = 2; //two players
        int rows = 3; //3 rows
        int cols = 3; //3 columns

        if(args.length == 3){
            playerCount = Integer.valueOf(args[0]);
            System.out.println(playerCount);
            rows = Integer.valueOf(args[1]);
            System.out.println(rows);
            cols = Integer.valueOf(args[2]);
            System.out.println(cols);

        }

        if(args.length == 2){
            playerCount = Integer.valueOf(args[0]);
            System.out.println(playerCount);
            rows = Integer.valueOf(args[1]);
            System.out.println(rows);
            cols = rows;
            System.out.println(cols);

        }

        //if 1 is less than or equal value of playerCount
        //add "player1.." as key and zero as value for all collection in gamesToWinsMap
        for(int i = 1; i <= playerCount; i++){
            gamesToWinsMap.put("player" + i , 0);
        }

        System.out.println(gamesToWinsMap);

        //lets play 100 games and see the wins and ties
        playGames(100,playerCount,rows,cols);

        System.out.println("Played 100 games");

        for(int i = 1; i <= playerCount; i++){
            System.out.println("Number wins by Player " + i + ": " + gamesToWinsMap.get("Player " + i));
        }

        System.out.println("Number of ties: " + gamesToWinsMap.get(TIE));
    }

    //overloading function
    public static void playGames(int gamesToPlay, int playerCount, int rows, int cols){
        //play a new game each iteration, in our example, count = 100
        for(int i = 0; i < gamesToPlay; i++){
            playGames(playerCount,rows,cols);
        }

    }

    public static void playGames(int playerCount,int rows, int cols){
        //create a new game board, this initalizes our 2nd array and lets the complexity of handling that
        //array be deligated to the board

        Board board = new Board(playerCount, rows, cols);

        //we are going to generate a random list of moves. Heres where we are going to store it
        List<Move> moves = new ArrayList<Move>();


        //we are creating moves for each space on the board
        for(int row = 0; row < rows; row++ ){
            //creating moves for each row
            for(int col = 0; col < cols;col++){
                //creating moves for each colums following each row
                moves.add(new Move(row,col));
            }
        }

//		System.out.println(moves);
//		System.out.println("-------------------------------------------------------------------------------------");

        //randomize the move list
        Collections.shuffle(moves);
        System.out.println(moves);

        //do each move
        for(Move move: moves){
            board.play(move.getRow(),move.getColumn());
//	    	System.out.println(move.hashCode());

            if(gameOver(board))
                break;
        }
//		System.out.println("Play game");




    }
    public static boolean gameOver(Board board){

        if(board.whoWon()!=null){
            System.out.println("\n" + board.whoWon() + " won the game!");
            System.out.println("Winning move: " + board.getWinningMove());
            System.out.println(board);

            Integer winCount = gamesToWinsMap.get(board.whoWon());
            System.out.println(winCount);
            winCount = winCount == null ? 1 : winCount +1;
//            System.out.println(winCount);
            gamesToWinsMap.put(board.whoWon(), winCount);

            return true;
        }
        //if has to send back at least 1 for the first move
        else if(board.movesLeft() == 0){
            Integer tieCount = gamesToWinsMap.get(TIE);
            tieCount = tieCount == null ? 1 : tieCount + 1;
            gamesToWinsMap.put(TIE,tieCount);
            return true;

        }
        return false;
    }



