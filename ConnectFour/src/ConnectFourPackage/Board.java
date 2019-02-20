/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectFourPackage;
import java.lang.*;
import java.util.*;
/**
 *
 * @author john
 */
class Board {
    int[][] board = new int[6][7];
    int turn; // 1 or 2
    int lastMove;
    int bestMove; // as a static variable for minimax
    Board(){ //constructor
        this.board = new int[6][7];
        int i,j;
        for(i=0;i<6;i++){
            for(j=0;j<7;j++){
                board[i][j] = 0;
            }
        }
        this.turn = 1;
        this.lastMove = -1;
        this.bestMove = -1;
    }
    public int[][] stateArray(){ // returns the board in array form, making it easier for AI to play the game.
        return this.board;
    }
    public void printStateArray(){ // prints the board to the stdout.
        int i,j;
        for(i=0;i<6;i++){
            for(j=0;j<7;j++){
                System.out.print(this.board[i][j]);
            }
            System.out.println();
        }
        return;
    }
    public boolean placeStone(int n)
    { // gets a column as an input, and changes the board according to the move
        if(n<0 && n>6){
            System.out.println("Invalid play");
            return false;
        }
        else if(this.board[0][n] != 0){
            System.out.println("Invalid play");
            return false;
        }
        int k;
        for(k=5;k>-1;k--){
            if(this.board[k][n] == 0){
                this.board[k][n] = turn;
                this.lastMove = n;
                return true;
            }
        }
        System.out.println("If control flow has reached here, something happened in the playTurn method.");
        return false;
    }
    public boolean playTurn(int n) { 
    // gets a column as an input, and changes the board according to the turn. Changes the turn.
        boolean validPlay = this.placeStone(n);
        if(validPlay != true){
            return false;
        }
        this.turn = this.turn==1?2:1;
        return true;
    }
    public int isDone(int player)
    { // checks if the game is over. returns 0 if it's not over, 1 if 1 won, and 2 if 2 won.
        int i,j;
        for(i=0;i<6;i++)
        {
            for(j=0;j<4;j++)
            {
                if(this.board[i][j] == this.board[i][j+1]
                        && this.board[i][j+1] == this.board[i][j+2]
                        && this.board[i][j+2] == this.board[i][j+3]
                        && this.board[i][j]==player){
                    //System.out.print((this.board[i][j] == 1)?1:2);
                    //System.out.println(" wins");
                    return j;
                }
            }
        } // row 
        for(j=0;j<7;j++)
        {
            for(i=0;i<3;i++)
            {
                if(this.board[i][j] == this.board[i+1][j]
                        && this.board[i+1][j] == this.board[i+2][j]
                        && this.board[i+2][j] == this.board[i+3][j]
                        && this.board[i][j]==player)
                {
                    //System.out.print((this.board[i][j] == 1)?1:2);
                    //System.out.println(" wins");
                    return j;
                }
            }
        } // column
        for(i=0;i<3;i++){ // right down diagonal
            for(j=0;j<4;j++){
                if(this.board[i][j] == this.board[i+1][j+1]
                        && this.board[i+1][j+1] == this.board[i+2][j+2]
                        && this.board[i+2][j+2] == this.board[i+3][j+3]
                        && this.board[i][j]==player) {
                    //System.out.print((this.board[i][j] == 1)?1:2);
                    //System.out.println(" wins");
                    return j;
                }
            }
        }
        for(i=0;i<3;i++){ // right up diagonal
            for(j=3;j<7;j++){
                if(this.board[i][j] == this.board[i+1][j-1]
                        && this.board[i+1][j-1] == this.board[i+2][j-2]
                        && this.board[i+2][j-2] == this.board[i+3][j-3]
                        && this.board[i][j]==player)
                {
                    //System.out.print((this.board[i][j] == 1)?1:2);
                    //System.out.println(" wins");
                    return j-3;
                }
            }
        }
        return -1; // the game isn't over yet
    }
    public int consecutiveThree(int player)
    { 
        int i,j;
        for(i=0;i<6;i++)
        {
            for(j=0;j<4;j++)
            {
                if(this.board[i][j] == this.board[i][j+1]
                        && this.board[i][j+1] == this.board[i][j+2]
                        && this.board[i][j]==player){
                    return j;
                }
            }
        } // row 
        for(j=0;j<7;j++)
        {
            for(i=0;i<3;i++)
            {
                if(this.board[i][j] == this.board[i+1][j]
                        && this.board[i+1][j] == this.board[i+2][j]
                        && this.board[i][j]==player)
                {
                    return j;
                }
            }
        } // column
        for(i=0;i<3;i++){ // right down diagonal
            for(j=0;j<4;j++){
                if(this.board[i][j] == this.board[i+1][j+1]
                        && this.board[i+1][j+1] == this.board[i+2][j+2]
                        && this.board[i][j]==player) {
                    return j;
                }
            }
        }
        for(i=0;i<3;i++){ // right up diagonal
            for(j=3;j<7;j++){
                if(this.board[i][j] == this.board[i+1][j-1]
                        && this.board[i+1][j-1] == this.board[i+2][j-2]
                        && this.board[i][j]==player)
                {
                    return j-2;
                }
            }
        }
        return -1;
    }
    public int consecutiveTwoWithNoSides(int player) { 
        int i,j;
        for(i=0;i<6;i++)
        {
            for(j=1;j<5;j++)
            {
                if(this.board[i][j] == this.board[i][j+1]
                        && this.board[i][j-1] == 0
                        && this.board[i][j+2] == 0
                        && this.board[i][j]==player){
                    return j;
                }
            }
        } // row 
        return -1;
    }
}

