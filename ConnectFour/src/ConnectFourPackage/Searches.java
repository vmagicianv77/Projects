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
/*
0 0 0 0 0 0 0
0 0 0 0 0 0 0
0 0 0 0 0 0 0
0 0 0 0 0 0 0
0 0 0 0 0 0 0
0 0 0 0 0 0 0
*/
class Searches {
    /*int monteCarloPoint(Board b, int player){
        Board temp = new Board();
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                temp.board[i][j] = b.board[i][j];
            }
        }
        temp.turn = b.turn;
        temp.lastMove = b.lastMove;
        while(temp.isDone(1) != -1 && temp.isDone(2)!=-1){
            
        }
        return 1;
    }*/
    public static int aStarMove(Board b){
        return 0;
    }
    public static int simulate(Board b){
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
    public static int evaluate(Board b){
        //evaluates the board
        //in the perspective of the current player
        //who holds the turn in the board
        int i, j, k;
        int points = 0;
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
                points += 50;
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
                points -= 50;
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
                points += 30;
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
                points -= 30;
            }
            for(i=0;i<6;i++){
                for(j=0;j<7;j++){
                    temp.board[i][j] = b.board[i][j];
                }
            }
            temp.turn = (b.turn==1)?2:1;
            temp.lastMove = b.lastMove;
        } // simulates if there's a chance to make three consecutives for the opponent
        temp.turn = b.turn;
        int ctwns = temp.consecutiveTwoWithNoSides(temp.turn); // just for convenience
        if(ctwns != -1) {
            points += 30;
        }
        temp.turn = b.turn==1?2:1;
        if(ctwns != -1){
            points -= 30;
        } // if there are consecutive twos with empty sides, returns the left side to block it
        return points;
    }
    public static int minimaxValue(Board b, int depth, boolean isMax){
        if(b.isDone(b.turn) != -1){
            return (isMax==true)?99999:-99999;
        }
        else if(depth<1){
            return evaluate(b);
        }
        else {
            if(isMax){
                int i,j;
                int value = 0;
                int bestValue = -99999;
                Board temp = new Board(); // initialize the temp board
                for(i=0;i<6;i++){
                    for(j=0;j<7;j++){
                        temp.board[i][j] = b.board[i][j];
                    }
                }
                temp.turn = b.turn;
                temp.lastMove = b.lastMove; // initialize the temp board
                for(i=0;i<7;i++){
                    if(temp.board[0][i]!=0){
                        //System.out.println("continued from minimaxValue");//TODO
                        continue;
                    } // if the move is unavailable, continue
                    temp.playTurn(i);
                    value = minimaxValue(temp, depth-1, false);
                    bestValue = value>bestValue?value:bestValue;
                    for(int k=0;k<6;k++){
                        for(int n=0;n<7;n++){
                            temp.board[k][n] = b.board[k][n];
                        }
                    }
                    temp.turn = b.turn;
                    temp.lastMove = b.lastMove;
                }
                return bestValue;
            }
            else { // Min 
                int i,j;
                int value = 0;
                int bestValue = 99999;
                Board temp = new Board(); // initialize the temp board
                for(i=0;i<6;i++){
                    for(j=0;j<7;j++){
                        temp.board[i][j] = b.board[i][j];
                    }
                }
                temp.turn = b.turn;
                temp.lastMove = b.lastMove;
                for(i=0;i<7;i++){
                    if(temp.board[0][i]!=0){
                        //System.out.println("continued from minimaxValue");//TODO
                        continue;
                    } // if the move is unavailable, continue
                    temp.playTurn(i);
                    value = minimaxValue(temp, depth-1, true);
                    bestValue = value<bestValue?value:bestValue;
                    for(int k=0;k<6;k++){
                        for(int n=0;n<7;n++){
                            temp.board[k][n] = b.board[k][n];
                        }
                    }
                    temp.turn = b.turn;
                    temp.lastMove = b.lastMove;
                }
                return bestValue;
            }
        }
    }
    public static int minimaxMove(Board b) {
        boolean isFirst = true;
        for(int n=0;n<7;n++){
            isFirst = isFirst && b.board[5][n]==0;
        }
        if(isFirst){
            return 2;
        }
        int[] values = new int[7];
        int i, j;
        Board temp = new Board(); // initialize the temp board
        for(i=0;i<6;i++){
            for(j=0;j<7;j++){
                temp.board[i][j] = b.board[i][j];
            }
        }
        temp.turn = b.turn;
        temp.lastMove = b.lastMove;      // initialize temp board
        for(i=0;i<7;i++){
            if(temp.board[0][i] != 0) {
                continue;
            }
            temp.playTurn(i);
            values[i] = minimaxValue(temp, 6, true);
            for(int k=0;k<6;k++){
                for(int n=0;n<7;n++){
                    temp.board[k][n] = b.board[k][n];
                }
            }
            temp.turn = b.turn;
            temp.lastMove = b.lastMove;
        }
        //with the values array filled, choose the max of them
        int min = 99999;
        int minMove = -1;
        for(i=0;i<7;i++){
            if(values[i]<min){
                min = values[i];
                minMove = i;
            }
        }
        //for(i=0;i<7;i++){
        //    System.out.print(values[i]+ "  ");
        //} // TODO
        System.out.println();
        return minMove;
    }
    /*public static int winways(Board b, int player){
    // a board has 69 ways of fitting 4 stones in a row.
    // That is, there are 69 ways to win
    // winways returns the leftover of these 69
        int[][] arr = b.stateArray();
        int i,j;
        int winways = 69;
        int opponent = (player==1)?2:1;
        for(i=0;i<6;i++){
            for(j=0;j<4;j++){
                if(arr[i][j] == opponent
                        || arr[i][j+1] == opponent
                        || arr[i][j+2] == opponent
                        || arr[i][j+3] == opponent)
                {
                    winways -= 1;
                }
            }
        } // row 
        for(j=0;j<7;j++){
            for(i=0;i<3;i++){
                if(arr[i][j] == opponent
                        || arr[i+1][j] == opponent
                        || arr[i+2][j] == opponent
                        || arr[i+3][j] == opponent)
                {
                    winways -= 1;
                }
            }
        } // column

        for(i=0;i<3;i++){ // right down diagonal
            for(j=0;j<4;j++){
                if(arr[i][j] == opponent
                        || arr[i+1][j+1] == opponent
                        || arr[i+2][j+2] == opponent
                        || arr[i+3][j+3] == opponent) 
                {
                    winways -= 1;
                }
            }
        }
        for(i=0;i<3;i++){ // right up diagonal
            for(j=3;j<7;j++){
                if(arr[i][j] == opponent
                        || arr[i+1][j-1] == opponent
                        || arr[i+2][j-2] == opponent
                        || arr[i+3][j-3] == opponent)
                {
                    winways -= 1;
                }            }
        }
        return winways;
    }*/
    /*
    public static int evaluate(Board b, int player){ 
    //evaluates the board in the perspective of the player
        if(b.isDone(player) != -1) {
            return 99999;
        }
        Board temp = new Board(); // initialize the temp board
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                temp.board[i][j] = b.board[i][j];
            }
        }
        temp.turn = b.turn;
        temp.lastMove = b.lastMove; // initialize the temp board        
        int extraPoints = 0;
        int howManyTurnsToWin = 0;
        int wnwys = winways(b,player);
        int whereToCheck = temp.consecutiveThree(player); // if no consecutive 3, -1
        if(whereToCheck != -1 && whereToCheck > 0) {
            for(int k=0;k<3;k++){
                if(temp.board[0][whereToCheck]==0){
                    temp.placeStone(whereToCheck);// Because you want to place the same stone again
                    //temp.printStateArray();//TODO
                }
                if(temp.isDone(player) != -1){
                    howManyTurnsToWin = k;
                    break;
                }
            }
        }
        if(whereToCheck != -1 && whereToCheck < 4 ){
            for(int k=0;k<3;k++){
                if(temp.board[0][whereToCheck+3]==0){
                    temp.placeStone(whereToCheck+3);
                    //temp.printStateArray();//TODO
                }
                if(temp.isDone(player) != -1){
                    howManyTurnsToWin = howManyTurnsToWin<k?howManyTurnsToWin:k;
                    break;
                }
            }
        }
        extraPoints = (4-howManyTurnsToWin)*20;
        return wnwys+extraPoints;
    }
    */
}    
    /*
    public static int[] minimaxValue(Board b, int depth, boolean isMax) {
        if(b.isDone()==0){
            int[] result = {-1, -1};
            return result;
        }
        else if(depth < 1){
            int[] result = {-1, evaluation(b, b.turn)};
            return result;
        }
        int value;
        int bestValue;
        int i,j;
        Board temp = new Board(); // initialize the temp board
        for(i=0;i<6;i++){
            for(j=0;j<7;j++){
                temp.board[i][j] = b.board[i][j];
            }
        }
        temp.turn = b.turn;
        temp.lastMove = b.lastMove;
        if(isMax){
            bestValue = -99999;
            for(i=0;i<7;i++){
                temp.playTurn(i);
                value = minimaxValue(temp, depth-1, false);
                if(value>bestValue){
                    bestValue = value;
                    b.bestMove = i;
                }
            }
            if(b.bestMove == -1){
                System.out.println("Something wrong with minimax");
                return 0;
            }
            return bestValue;
        }
        else {
            bestValue = 99999;
            for(i=0;i<7;i++){
                temp.playTurn(i);
                value = minimaxValue(temp, depth-1, false);
                if(value<bestValue){
                    bestValue = value;
                    b.bestMove = i;
                }
            }
            if(b.bestMove == -1){
                System.out.println("Something wrong with minimax");
                return 0;
            }
            return bestValue;
        }
    }
    public static int monteCarlo(Board b){
        Board temp;
        
    }*/
/*public static int evaluation(Board b, int player){
        if(b.isDone(player)!=-1){
            return 99999;
        }
        int extraPoints = 0;
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
                extraPoints += 50;
            }
            for(i=0;i<6;i++){
                for(j=0;j<7;j++){
                    temp.board[i][j] = b.board[i][j];
                }
            }
            temp.turn = b.turn;
            temp.lastMove = b.lastMove;//initialize the temp board again
        } // simulates if there's a game ending chance for the player
        for(k=0;k<7;k++){
            validPlay = false;
            if(temp.board[0][k]==0){
                validPlay = temp.placeStone(k);
            }
            if(temp.consecutiveThree(temp.turn)!=-1 && validPlay){
                extraPoints += 30;
            }
            for(i=0;i<6;i++){
                for(j=0;j<7;j++){
                    temp.board[i][j] = b.board[i][j];
                }
            }
            temp.turn = b.turn;
            temp.lastMove = b.lastMove;
        } // simulates if there's a chance to make three consecutives for the player
        int wnwys = winways(b, player);
        return wnwys + extraPoints;
    }*/
