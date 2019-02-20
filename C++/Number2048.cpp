#include <iostream>
#include <vector>
using namespace std;
class Board{
    public:
    vector<vector<int> > board;
    Board(int n){
        for(int i=0;i<n;i++){
            board.push_back(vector<int>(n));
        }
    }
    void printBoard(){
        for(int i=0;i<board.size();i++){
            for(int j=0;j<board[0].size();j++){
                cout<<board[i][j];
            }
            cout<<"\n";
        }
        return;
    }
    
    void move(int direction){
        int y,x;
        vector<vector<bool> > collapsed;
        for(int i=0;i<board.size();i++){
            collapsed.push_back(vector<bool>(board.size()));
        }
        for(int i=0;i<collapsed.size();i++){
            for(int j=0;j<collapsed[0].size();j++){
                collapsed[i][j] = false;
            }
        }
        switch(direction){
            case 0:
                for(int i=0;i<board.size();i++){
                    for(int j=0;j<board.size();j++){
                        y = i;
                        x = j;
                        while(y>0 && board[y-1][x]==0){
                            board[y-1][x] = board[y][x];
                            board[y][x] = 0;
                            y -= 1;
                        }
                        if(y>0 && board[y-1][x] == board[y][x] && collapsed[y-1][x]==false){
                            board[y-1][x] *= 2;
                            board[y][x] = 0;
                            collapsed[y-1][x] = true;
                        }
                    }
                }
                break;
            case 1:
                for(int i=0;i<board.size();i++){
                    for(int j=board.size()-1;j>-1;j--){
                        y = i;
                        x = j;
                       
                        while(x<board.size()-1 && board[y][x+1]==0){
                            board[y][x+1] = board[y][x];
                            board[y][x] = 0;
                            x += 1;
                        }
                        if(x<board.size()-1 && board[y][x+1] == board[y][x] && !collapsed[y][x+1]){
                            board[y][x+1] *= 2;
                            board[y][x] = 0;
                            collapsed[y][x+1] = true;
                        }
                    }
                }
                break;
            case 2:
                for(int i=board.size()-1;i>-1;i--){
                    for(int j=0;j<board.size();j++){
                        y = i;
                        x = j;
                        
                        while(y<board.size()-1 && board[y+1][x]==0){
                            board[y+1][x] = board[y][x];
                            board[y][x] = 0;
                            y += 1;
                        }
                        if(y<board.size()-1 && board[y+1][x] == board[y][x] && !collapsed[y+1][x]){
                            board[y+1][x] *= 2;
                            board[y][x] = 0;
                            collapsed[y+1][x] = true;
                        }
                    }
                }
                break;
            case 3:
                for(int i=0;i<board.size();i++){
                    for(int j=0;j<board.size();j++){
                        y = i;
                        x = j;
                        while(x>0 && board[y][x-1]==0){
                            board[y][x-1] = board[y][x];
                            board[y][x] = 0;
                            x -= 1;
                        }
                        if(x>0 && board[y][x-1] == board[y][x] && !collapsed[y][x-1]){
                            board[y][x-1] *= 2;
                            board[y][x] = 0;
                            collapsed[y][x-1] = true;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return;
    }
    int biggestBlock(){
        int biggest = board[0][0];
        for(int i=0;i<board.size();i++){
            for(int j=0;j<board.size();j++){
                biggest = max(biggest, board[i][j]);
            }
        }
        return biggest;
    }
    Board clone(){
        Board newBoard = Board(board.size());
        for(int i=0;i<board.size();i++){
            for(int j=0;j<board.size();j++){
                newBoard.board[i][j] = board[i][j];
            }
        }
        return newBoard;
    }
};
int number2048(int moves, Board &board){
    if(moves==5){
        return board.biggestBlock();
    }
    int maxBlock = 0;
    for(int i=0;i<4;i++){
        Board newBoard = board.clone();
        board.move(i);
        maxBlock=max(maxBlock, solve(moves+1, board));
        board = newBoard.clone();
    }
    return maxBlock;
}
int main(void){
    int n;
    cin>>n;
    Board thisBoard = Board(n);
    int tempInt;
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cin>>tempInt;
            thisBoard.board[i][j] = tempInt;
        }
    }
    cout<<number2048(0,thisBoard);
    return 0;
}