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
public class ConnectFourMain {
    public static void main(String args[]) {
        Board b = new Board();
        Scanner sc;
        sc = new Scanner(System.in);
        int userInput;
        int modeInput;
        //b.printStateArray();
        //System.out.println(b.isDone());
        while(b.isDone(1) == -1 && b.isDone(2) == -1){
            b.printStateArray();
            System.out.println("1 => heuristic search play\n2 => simple rules play\n3 => user play");
            modeInput = sc.nextInt();
            if(modeInput == 2){
                b.playTurn(SimpleRules.simpleRulePlay(b));
            }
            else if(modeInput == 1){
                b.playTurn(Searches.minimaxMove(b));
            }
            else if(modeInput == 3){
                boolean validPlay;
                do {
                    System.out.println("Which column would you place the stone?");
                    userInput = sc.nextInt();
                    validPlay = b.playTurn(userInput);
                }while(!validPlay);
            }
            else{
                return;
            }
        }
        b.printStateArray();
        return;
    }
    /*public static void main(String args[]) {
        Board b = new Board();
        Scanner sc;
        sc = new Scanner(System.in);
        int userInput;
        //b.printStateArray();
        //System.out.println(b.isDone());
        while(b.isDone(1) == -1 && b.isDone(2) == -1){
            b.printStateArray();
            System.out.printf("Player %d lay your stone\n", b.turn);
            userInput = sc.nextInt();
            if(userInput == 9999){
                return;
            }
            b.playTurn(userInput-1);
            //b.printStateArray();
            b.playTurn(Searches.minimaxMove(b));
            //b.printStateArray();
        }
        b.printStateArray();
        return;
    }*/
    /*public static void main(String args[]){
        Board b = new Board();
        Scanner sc = new Scanner(System.in);
        int userInput;
        while(b.isDone(1) == -1 && b.isDone(2)==-1){
            b.printStateArray();
            System.out.printf("Player %d lay your stone\n", b.turn);
            userInput = sc.nextInt();
            if(userInput == 9999){
                return;
            }
            b.playTurn(userInput-1);
            System.out.println(Searches.evaluate(b));
        }
        return;
    }*/
}
