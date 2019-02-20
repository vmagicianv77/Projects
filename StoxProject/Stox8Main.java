
package Stox8Package;
import java.lang.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author john
 */
public class Stox8Main {
    public static void main(String[] args) {
        String dir = "/home/john/Desktop/Stocks";
        StockUtils stockutils = new StockUtils();
        stockutils.initialize();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Will you initialize? Y or N");
        char userAnswer = scanner.next().charAt(0);
        if(userAnswer == 'Y'){
            stockutils.makeStockFiles(dir);
        } 
        System.out.println("Will you update? Y or N");
        userAnswer = scanner.next().charAt(0);
        if(userAnswer =='Y'){
            for(String s : stockutils.stocks){
                stockutils.updateStock(dir, s);
            }
        }
        stockutils.suggest(dir, 5, 1, 15, false);
        return;
    }
}
