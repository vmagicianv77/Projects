
package Stox8Package;
import java.io.*;
import java.util.*;
import java.lang.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
/**
 *
 * @author john
 */
public class Stock implements Serializable {
    int number;
    String name;
    String numberString;
    double per;
    double pbr;
    double roe;
    ArrayList<Double> prices;
    Stock(String num){
        this.name=new String();
        this.numberString= new String(num);
        this.prices = new ArrayList<Double>();
    }
    boolean bollinger(){
        try{
            double sum = 0;
            double average = 0;
            double variance = 0;
            double standardDeviation = 0;
            for(int i=this.prices.size()-1;i>this.prices.size()-21;i--){
                sum += this.prices.get(i);
            }
            average = sum/(this.prices.size()+1);
            for(int i=this.prices.size()-1;i>this.prices.size()-21;i--){
                variance += (average-this.prices.get(i))*(average-this.prices.get(i));
            }
            variance /= (this.prices.size()+1);
            standardDeviation = Math.sqrt(variance);
            if(this.prices.get(this.prices.size()-1)<=average-standardDeviation){
                return true;
            } else {
                return false;
            }
        }catch(IndexOutOfBoundsException e){
            return false;
        }
    }
}