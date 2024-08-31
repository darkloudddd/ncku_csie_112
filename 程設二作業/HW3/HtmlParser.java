import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.math.*;
import java.util.*;

public class HtmlParser {

    public static final int STOCKMAXNUM = 133;
    public static final int PRICENUM = 30;

    public static StockInfo[] parser() {

        StockInfo[] stockInfo_clsArr = new StockInfo[STOCKMAXNUM];
        for (int i = 0; i < STOCKMAXNUM; i++) {
            stockInfo_clsArr[i] = new StockInfo();
        }

        try {
            Document doc = Jsoup.connect("https://pd2-hw3.netdb.csie.ncku.edu.tw/").get();

            int day = Integer.parseInt(doc.title().substring(3));
            Elements names = doc.select("th");
            Elements prices = doc.select("td");

            int idxOfStockInfo = 0;
            for (Element name : names) {
                stockInfo_clsArr[idxOfStockInfo].stockName = name.text();
                idxOfStockInfo++;
            }
            idxOfStockInfo = 0;
            for (Element price : prices) {
                stockInfo_clsArr[idxOfStockInfo].stockPrice[day - 1] = Float.parseFloat(price.text());
                idxOfStockInfo++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockInfo_clsArr;
    }

    public static void mode0(StockInfo[] stockInfo_clsArr) {

        try (PrintWriter pw = new PrintWriter(new File("data.csv"))) {

            StringBuilder sb = new StringBuilder();

            sb.append(stockInfo_clsArr[0].stockName);
            for (int idxOfStockInfoClsArr = 1; idxOfStockInfoClsArr < STOCKMAXNUM; idxOfStockInfoClsArr++) {

                if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName == null) {
                    break;
                }
                sb.append(",").append(stockInfo_clsArr[idxOfStockInfoClsArr].stockName);
            }

            for (int day = 0; day < PRICENUM; day++) {

                sb.append("\n").append(stockInfo_clsArr[0].stockPrice[day]);
                for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < STOCKMAXNUM; idxOfStockInfoClsArr++) {

                    if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName == null) {
                        break;
                    }
                    sb.append(",").append(stockInfo_clsArr[idxOfStockInfoClsArr].stockPrice[day]);
                }

            }

            pw.write(sb.toString());
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void task0() {

        String output_str = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                output_str += line + "\n";
            }
            output_str = output_str.substring(0, output_str.length() - 1);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("output.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(output_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void task1(StockInfo[] stockInfo_clsArr, String name, int start, int end) {

        String output_str = name + "," + start + "," + end + "\n";
        float[] price_arr_f = new float[PRICENUM];
        float[] avrPrice_arr_f = new float[26];

        for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < STOCKMAXNUM; idxOfStockInfoClsArr++) {

            if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName != null
                    && stockInfo_clsArr[idxOfStockInfoClsArr].stockName.equals(name)) {

                for (int idxOfStockPrice = 0; idxOfStockPrice < PRICENUM; idxOfStockPrice++) {
                    price_arr_f[idxOfStockPrice] = stockInfo_clsArr[idxOfStockInfoClsArr].stockPrice[idxOfStockPrice];
                }
                break;
            }
        }

        for (int i = 0; i < 26; i++) {
            BigDecimal bd = new BigDecimal(
                    (price_arr_f[i] + price_arr_f[i + 1] + price_arr_f[i + 2] + price_arr_f[i + 3] + price_arr_f[i + 4])
                            / 5);
            Float avrPrice = Float.parseFloat(bd.setScale(2).toString());
            avrPrice_arr_f[i] = avrPrice;
        }

        output_str += avrPrice_arr_f[0];
        for (int i = 1; i < 26; i++) {
            output_str += "," + avrPrice_arr_f[i];
        }

        try {
            File file = new File("output.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(output_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static double standardDeviationCalculator(StockInfo[] stockInfo_clsArr, String name, int start, int end) {
        float[] price_arr_f = new float[PRICENUM];
        float priceSum_f = 0;
        double avrPrice_d;
        double priceDiffSqrSum_d = 0.0;
        double s_d;
        int n = end - start + 1;

        for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < STOCKMAXNUM; idxOfStockInfoClsArr++) {

            if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName != null
                    && stockInfo_clsArr[idxOfStockInfoClsArr].stockName.equals(name)) {

                for (int idxOfStockPrice = 0; idxOfStockPrice < PRICENUM; idxOfStockPrice++) {
                    price_arr_f[idxOfStockPrice] = stockInfo_clsArr[idxOfStockInfoClsArr].stockPrice[idxOfStockPrice];
                }
                break;
            }
        }

        for (int idxOfPrice = start - 1; idxOfPrice < end; idxOfPrice++) {
            priceSum_f += price_arr_f[idxOfPrice];
        }
        avrPrice_d = priceSum_f / n;

        for (int idxOfpriceDiff = start - 1; idxOfpriceDiff < end; idxOfpriceDiff++) {
            priceDiffSqrSum_d += Math.pow(price_arr_f[idxOfpriceDiff] - avrPrice_d, 2);
        }

        BigDecimal bd = new BigDecimal(Math.sqrt(priceDiffSqrSum_d / (n - 1)));
        s_d = Double.parseDouble(bd.setScale(2).toString());
        return s_d;
    }

    public static void task2(StockInfo[] stockInfo_clsArr, String name, int start, int end) {

        String output_str = name + "," + start + "," + end + "\n";

        output_str += standardDeviationCalculator(stockInfo_clsArr, name, start, end);

        try {
            File file = new File("output.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(output_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void task3(StockInfo[] stockInfo_clsArr, int start, int end) {

        SdInfo[] sdInfo_arr = new SdInfo[STOCKMAXNUM];

        for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < STOCKMAXNUM; idxOfStockInfoClsArr++) {
            sdInfo_arr[idxOfStockInfoClsArr].stockName = stockInfo_clsArr[idxOfStockInfoClsArr].stockName;
            sdInfo_arr[idxOfStockInfoClsArr].s = standardDeviationCalculator(stockInfo_clsArr,
                    stockInfo_clsArr[idxOfStockInfoClsArr].stockName, start, end);
        }

        Arrays.sort(sdInfo_arr, Collections.reverseOrder());

        String output_str = sdInfo_arr[0].stockName + "," + sdInfo_arr[1].stockName + "," + sdInfo_arr[2].stockName
                + "\n";
        output_str += sdInfo_arr[0].s + "," + sdInfo_arr[1].s + "," + sdInfo_arr[3].s;

        try {
            File file = new File("output.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(output_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void task4(StockInfo[] stockInfo_clsArr, String name, int start, int end) {

        String output_str = name + "," + start + "," + end + "\n";

        float[] price_arr_f = new float[PRICENUM];
        float priceSum_f = 0;
        double avrPrice_d;
        double avrT_d;

        double b0, b1, up = 0.0, down = 0.0;

        for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < STOCKMAXNUM; idxOfStockInfoClsArr++) {

            if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName != null
                    && stockInfo_clsArr[idxOfStockInfoClsArr].stockName.equals(name)) {

                for (int idxOfStockPrice = 0; idxOfStockPrice < PRICENUM; idxOfStockPrice++) {
                    price_arr_f[idxOfStockPrice] = stockInfo_clsArr[idxOfStockInfoClsArr].stockPrice[idxOfStockPrice];
                }
                break;
            }
        }

        for (int idxOfPrice = start - 1; idxOfPrice < end; idxOfPrice++) {
            priceSum_f += price_arr_f[idxOfPrice];
        }
        avrPrice_d = priceSum_f / (end - start + 1);
        avrT_d = (start + end) / 2;

        for (int idx = start - 1; idx < end; idx++) {
            up += (idx + 1 - avrT_d) * (price_arr_f[idx] - avrPrice_d);
        }
        for (int idx = start - 1; idx < end; idx++) {
            down += Math.pow(idx + 1 - avrT_d, 2);
        }
        BigDecimal bd = new BigDecimal(up / down);
        b1 = Double.parseDouble(bd.setScale(2).toString());

        BigDecimal bd2 = new BigDecimal(avrPrice_d - b1 * avrT_d);
        b0 = Double.parseDouble(bd2.setScale(2).toString());

        output_str += b1 + "," + b0;

        try {
            File file = new File("output.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(output_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String _input_mode_str = args[0];// store mode
        StockInfo[] _StockInfo_clsArr = new StockInfo[STOCKMAXNUM];

        if (_input_mode_str.equals("0") && args.length == 1) { // mode = 0

            _StockInfo_clsArr = parser();
            mode0(_StockInfo_clsArr);

        } else if (_input_mode_str.equals("1") && args.length > 1) { // mode = 1

            String _input_task_str = args[1]; // task = 0, 1, 2, 3, 4
            String _input_stockName_str = ""; // not use in task 3!!
            String _input_start_str;
            int _input_start_i = 0;
            String s_input_end;
            int _input_end_i = 0;

            if (args.length > 2) { // for task1 ~ task4
                _input_stockName_str = args[2]; // not use in task 3!!

                _input_start_str = args[3];
                _input_start_i = Integer.parseInt(_input_start_str);

                s_input_end = args[4];
                _input_end_i = Integer.parseInt(s_input_end);
            }

            switch (_input_task_str) {

                case "0":
                    task0();
                    break;

                case "1":
                    task1(_StockInfo_clsArr, _input_stockName_str, _input_start_i, _input_end_i);
                    break;

                case "2":
                    task2(_StockInfo_clsArr, _input_stockName_str, _input_start_i, _input_end_i);
                    break;

                case "3":
                    task3(_StockInfo_clsArr, _input_start_i, _input_end_i);
                    break;

                case "4":
                    task4(_StockInfo_clsArr, _input_stockName_str, _input_start_i, _input_end_i);
                    break;

                default:
                    System.out.println("task error!");
                    break;
            }
        }
    }
}

class StockInfo {
    String stockName;
    float[] stockPrice = new float[30]; // day1 ~ day30
}

class SdInfo implements Comparable<SdInfo> {

    String stockName;
    double s;

    public int compareTo(SdInfo o) {
        return (int) (this.s - o.s);
    }
}
