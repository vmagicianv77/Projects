/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectFourPackage;

/**
 *
 * @author john
 */
class SimpleRules {
    // If there's a move that will make four consecutives or three consecutives,
    // returns the move as an int.
    // If there are no moves that will make four or three consecutive stones,
    // returns -1
    public static int simulate(Board b){
        // simulates the board in the perspective of
        // the player who holds the current turn, in the board.
        int i, j, k;
        Board temp = new Board();
        for(i=0;i<6;i++){
            for(j=0;j<7;j++){
                temp.board[i][j] = b.board[i][j];
            }
        }
        temp.turn = b.turn;
        temp.lastMove = b.lastMove; // initialize temp board
        boolean validPlay;
        for(k=0;k<7;k++){
            validPlay = false;
            if(temp.board[0][k]==0){
                validPlay = temp.placeStone(k);
            }
            if(temp.isDone(temp.turn)!=-1 && validPlay){
                return k;
            }
            for(i=0;i<6;i++){
                for(j=0;j<7;j++){
                    temp.board[i][j] = b.board[i][j];
                }
            }
            temp.turn = b.turn;
            temp.lastMove = b.lastMove;
        } // simulates if there's a game ending chance for the player
        
        temp.turn = (b.turn==1)?2:1;
        for(k=0;k<7;k++){
            validPlay = false;
            if(temp.board[0][k]==0){
                validPlay = temp.placeStone(k);
            }
            if(temp.isDone(temp.turn)!=-1 && validPlay){
                return k;
            }
            for(i=0;i<6;i++){
                for(j=0;j<7;j++){
                    temp.board[i][j] = b.board[i][j];
                }
            }
            temp.turn = (b.turn==1)?2:1;
            temp.lastMove = b.lastMove;
        } // simulates if there's a game ending chance for the opponent
        
        temp.turn = b.turn;
        for(k=0;k<7;k++){
            validPlay = false;
            if(temp.board[0][k]==0){
                validPlay = temp.placeStone(k);
            }
            if(temp.consecutiveThree(temp.turn)!=-1 && validPlay){
                return k;
            }
            for(i=0;i<6;i++){
                for(j=0;j<7;j++){
                    temp.board[i][j] = b.board[i][j];
                }
            }
            temp.turn = b.turn;
            temp.lastMove = b.lastMove;
        } // simulates if there's a chance to make three consecutives for the player
        
        temp.turn = (b.turn==1)?2:1;
        for(k=0;k<7;k++){
            validPlay = false;
            if(temp.board[0][k]==0){
                validPlay = temp.placeStone(k);
            }
            if(temp.consecutiveThree(temp.turn)!=-1 && validPlay){
                return k;
            }
            for(i=0;i<6;i++){
                for(j=0;j<7;j++){
                    temp.board[i][j] = b.board[i][j];
                }
            }
            temp.turn = (b.turn==1)?2:1;
            temp.lastMove = b.lastMove;
        } // simulates if there's a chance to make three consecutives for the opponent
        int ctwns = temp.consecutiveTwoWithNoSides(temp.turn); // just for convenience
        if(ctwns != -1 && ctwns-1>=0){
            return ctwns -1 ;
        } // if there are consecutive twos with empty sides, returns the left side to block it
        else if(ctwns != -1 && ctwns+2<7){
            return ctwns + 2;
        }
        return -1;
    }
    // Tests if there are any moves that will make four consecutives or three consecutives,
    // And if there are any, plays there.
    // And if there is none, plays the opponent's play, which places it on top.
    // But if that is over the board, plays randomly.
    public static int simpleRulePlay(Board b){
        boolean isFirst = true;
        for(int n=0;n<7;n++){
            isFirst = isFirst && b.board[5][n]==0;
        }
        if(isFirst){
            return 2;
        }
        int k;
        k = simulate(b);
        if(k!=-1){
            return k;
        }
        else {
            if(b.board[0][b.lastMove] == 0) {
                return b.lastMove;
            }
            else {
                for(int i=0;i<4;i++){
                    if(b.board[0][3-i] == 0){
                        return 3-i;
                    }
                    else if(b.board[0][3+i] == 0){
                        return 3+i;
                    }       
                }
            }
        }
        return 0;
    }
}
