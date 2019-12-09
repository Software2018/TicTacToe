package com.jonas.example.TicTacToe.board;

import org.apache.commons.lang3.ObjectUtils;

public class Move {

    private final int row; //number of rows
    private final int column; //number of columns
    private final Integer player; //number of players
    private final int hashCode; //hashCode

    //constructor "function call when class is first initialiate
    public Move(int row,int column){

        //setting contructor of 2 paramenter variables row and column
        this.row = row;
        this.column = column;
        this.player = -1;  //player is equal to -1
        hashCode = ObjectUtils.hashCodeMulti(row,column,player); //number of the
        // hascode in integer from using hashCodeMulti

    }

    public Move(int row, int column, int player) {
        this.row = row;
        this.column = column;
        this.player = player;
        hashCode = ObjectUtils.hashCodeMulti(row, column, player);
    }


    public int getRow() {
        //method to get row number
        return row;
    }

    public int getColumn() {
        // method to get column number
        return column;
    }

    public Integer getPlayer() {
        //method to get player number
        return player;
    }

    @Override
    public boolean equals(Object obj){
        //check if two object are equal
        if(!(obj instanceof Move)){ //if this is not a class of this return false
            return false;
        }

        //type masking of the argument
        Move move = (Move) obj;
        // boolean type of equal
        // this.getColumn() == move. getColumn argument
        // all methods are equals to the argument methods
        boolean equals = getColumn() == move.getColumn();
        equals = equals && getRow() == move.getRow();
        equals = equals && getPlayer() == move.getPlayer();

        return equals;


    }

    @Override
    //return the hashCode in Integer
    public int hashCode(){
        return hashCode;
    }



    @Override
    public String toString(){
        return "(" + getRow() + "," + getColumn() + ") - Player" +getPlayer();
    }

}
