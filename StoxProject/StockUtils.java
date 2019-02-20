
package Stox8Package;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
/**
 *
 * @author john
 */
public class StockUtils {
    ArrayList<String> stocks;
    StockUtils(){
        this.stocks = new ArrayList<String>();
    }
    public boolean initialize(){
        String s;
        try{
            Connection con = Jsoup.connect("http://vip.mk.co.kr/newSt/rate/item_all.php?koskok=KOSPI&orderBy=dd");
            Document doc = con.get();
            Elements elements = doc.select(".st2>a");
            for(Element each:elements){
                s = each.attr("title");
                this.stocks.add(s);
            }
        }catch(IOException e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    public void makeStockFiles(String dir){
        for(String s:stocks){
            Stock stock = new Stock(s);
            try{
                File file = new File(dir+"/"+s+".ser");
                file.createNewFile();
                file.setReadable(true);
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(stock);
                oos.close();
                fos.close();
            }catch(IOException e){
                System.out.println(e);
            }
            stock = null;
        }
        return;
    }
    public void updateStock(String dir, String stockNum){
        Stock stock;
        try{
            File file = new File(dir+"/"+stockNum+".ser");
            file.setReadable(true);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            stock = (Stock)ois.readObject();
            ois.close();
            fis.close(); 
            //
            file = new File(dir+"/"+stockNum+".ser");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Connection con = Jsoup.connect("https://finance.naver.com/item/main.nhn?code="+stockNum);
            Document doc = con.get();
            // per
            String perData;
            Elements perElements = doc.select("#_per");
            if(perElements.first()!=null) perData=perElements.first().text();
            else perData="9999";
            try{
                double d = Double.parseDouble(perData);
                System.out.println(d);
                stock.per = d;
            } catch(NumberFormatException e){
                stock.per = 9999;
            }
            // pbr
            String pbrData;
            Elements pbrElements = doc.select("#_pbr");
            if(pbrElements.first()!=null) pbrData=pbrElements.first().text();
            else pbrData="9999";
            try{
                double d = Double.parseDouble(pbrData);
                System.out.println(d);
                stock.pbr = d;
            } catch(NumberFormatException e){
                stock.pbr = 9999;
            }
            // roe
            String roeData;
            Elements roeElements = doc.select("th.h_th2.th_cop_comp12+td");
            if(roeElements.first()!=null) roeData=roeElements.first().text();
            else roeData="0";
            try{
                double d = Double.parseDouble(roeData);
                System.out.println(d);
                stock.roe = d;
            } catch(NumberFormatException e){
                stock.roe = 0;
            }
            
            // name
            String nameData;
            Elements nameElements = doc.select("title");
            if(nameElements.first()!=null) nameData=nameElements.first().text();
            else nameData="0";
            stock.name = nameData.split(" ")[0];
            
            // price
            String priceData;
            Elements priceElements = doc.select("dl>dd");
            if(priceElements.first()!=null) priceData=priceElements.get(4).text().split(" ")[1].replaceAll(",","");
            else priceData="0";
            try{
                double d = Double.parseDouble(priceData);
                System.out.println(d);
                stock.prices.add(d);
            } catch(NumberFormatException e){
                stock.prices.add(-1.0);
                System.out.println(e);
            }
            
            // output stream
            oos.writeObject(stock);
            oos.close();
            fos.close();
            stock=null;
        }catch(IOException e){
            System.out.println(e);
        }catch(ClassNotFoundException e){
            System.out.println(e);
        }
    }
    public void suggest(String dir, int per, int pbr, int roe, boolean bollinger){
        for(String s : stocks){
            try{
                File file = new File(dir+"/"+s+".ser");
                file.setReadable(true);
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Stock stock = (Stock)ois.readObject();
                if(stock.per<per && stock.pbr<pbr && stock.roe>roe && stock.bollinger() == bollinger){
                    System.out.println(stock.numberString+" "+stock.name);
                }
                ois.close();
                fis.close();
            }catch(IOException e){
                System.out.println(e);
            }catch(ClassNotFoundException e){
                System.out.println(e);
            }
        }
    }
}
