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
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.lang.Comparable;

public class HtmlParser {

    public static final int stockNum = 133;
    public static final int priceNum = 30;

    public static StockInfo[] parser() {

        // System.out.println("!!!!!!!!!!!!!start parser!!!!!!!!!!!!!");

        StockInfo[] stockInfo_clsArr = new StockInfo[stockNum];
        for (int i = 0; i < stockNum; i++) {
            stockInfo_clsArr[i] = new StockInfo();
        }

        try {
            Document doc = Jsoup.connect("https://pd2-hw3.netdb.csie.ncku.edu.tw/").get();

            int day = Integer.parseInt(doc.title().substring(3));
            Elements names = doc.select("th");
            Elements prices = doc.select("td");

            int idxOfStockInfo = 0;
            for (Element name : names) {
                // System.out.print("store name" + idxOfStockInfo + ", ");

                stockInfo_clsArr[idxOfStockInfo].stockName = name.text();
                idxOfStockInfo++;
            }
            idxOfStockInfo = 0;

            // System.out.println();

            for (Element price : prices) {
                // System.out.print("store price" + idxOfStockInfo + ", ");

                stockInfo_clsArr[idxOfStockInfo].stockPrice[day - 1] = Float.parseFloat(price.text());
                idxOfStockInfo++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockInfo_clsArr;
    }

    public static void mode0(StockInfo[] stockInfo_clsArr) // file not exist
            throws IOException {

        // System.out.println("\n\n!!!!!!!!!!!!!!start mode0!!!!!!!!!!!!!!!\n");

        String content = "";

        File file = new File("/home/F74126107/hw3/data.csv");
        if (!file.exists() || file.toString().equals("")) { // data.csv hasn't exist

            // System.out.print("contant add name" + 0 + ", ");

            content += stockInfo_clsArr[0].stockName;
            for (int idxOfStockInfoClsArr = 1; idxOfStockInfoClsArr < stockNum; idxOfStockInfoClsArr++) {

                if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName != null) {
                    // System.out.print("contant add name" + idxOfStockInfoClsArr + ", ");

                    content += String.format(",%s", stockInfo_clsArr[idxOfStockInfoClsArr].stockName);
                }
            }
        }

        int i;
        for (i = 0; i < priceNum; i++) {

            if (stockInfo_clsArr[0].stockPrice[i] != 0) {
                // System.out.println("\n\n\n!!!!!!!!!!!!find i !!!!!!!! " + i + "\n\n");
                break;
            }
        }

        // System.out.print("contant add price" + 0 + ", ");

        content += String.format("\n%.2f", stockInfo_clsArr[0].stockPrice[i]);
        for (int idxOfStockInfoClsArr = 1; idxOfStockInfoClsArr < stockNum; idxOfStockInfoClsArr++) {

            if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName != null) {
                // System.out.print("contant add price" + idxOfStockInfoClsArr + ", ");

                content += String.format(",%.2f", stockInfo_clsArr[idxOfStockInfoClsArr].stockPrice[i]);
            }
        }

        FileWriter fw = new FileWriter("data.csv", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();

    }

    public static StockInfo[] storeToDataBase() {

        StockInfo[] stockInfo_clsArr = new StockInfo[stockNum];
        for (int i = 0; i < stockNum; i++) {
            stockInfo_clsArr[i] = new StockInfo();
        }

        String wholeData = "";
        try {

            Path pathOfData_csv = Path.of("/home/F74126107/hw3/data.csv");
            wholeData = Files.readString(pathOfData_csv);

            // System.out.print("!!!!!!!!!!!!read data.csv to string!!!!!!!!!!!\n\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] dataLine = wholeData.split(System.lineSeparator());
        String[] names = dataLine[0].split(",");
        for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < stockNum; idxOfStockInfoClsArr++) {
            stockInfo_clsArr[idxOfStockInfoClsArr].stockName = names[idxOfStockInfoClsArr];

            // System.out.print("store name" + idxOfStockInfoClsArr + ", ");
        }

        // System.out.println();

        String[] prices = new String[stockNum];

        for (int idxOfDataLine = 1; idxOfDataLine < 31; idxOfDataLine++) {

            prices = dataLine[idxOfDataLine].split(",");

            // System.out.println("\n\n!!!!!!!!line" + idxOfDataLine + " has been
            // separate!!!!!!!");

            for (int idx = 0; idx < stockNum; idx++) {

                stockInfo_clsArr[idx].stockPrice[idxOfDataLine - 1] = Float.parseFloat(prices[idx].toString());

                // System.out.print("store price" + idx + ", ");

            }
        }

        return stockInfo_clsArr;
    }

    public static void task0() {

        String output_str = "";
        try {

            Path pathOfData_csv = Path.of("/home/F74126107/hw3/data.csv");
            output_str = Files.readString(pathOfData_csv);

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
        float[] price_arr_f = new float[priceNum];
        float[] avrPrice_arr_f = new float[26];

        for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < stockNum; idxOfStockInfoClsArr++) {

            if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName != null
                    && stockInfo_clsArr[idxOfStockInfoClsArr].stockName.equals(name)) {

                // System.out.print("!!!!!!!!!find the name at " + idxOfStockInfoClsArr + " of
                // stockinfoclsarr!!!!!!!!!!!!!\n\n");

                for (int idxOfStockPrice = 0; idxOfStockPrice < priceNum; idxOfStockPrice++) {

                    price_arr_f[idxOfStockPrice] = stockInfo_clsArr[idxOfStockInfoClsArr].stockPrice[idxOfStockPrice];

                    // System.out.print("store price" + idxOfStockPrice + " to pricearr, ");
                }
                break;
            }
        }

        // System.out.println("\n");

        for (int i = start - 1; i < end - 4; i++) {
            BigDecimal bd = new BigDecimal(
                    (price_arr_f[i] + price_arr_f[i + 1] + price_arr_f[i + 2] + price_arr_f[i + 3] + price_arr_f[i + 4])
                            / 5);

            Float avrPrice = Float.parseFloat(bd.setScale(2, RoundingMode.HALF_UP).toString());
            avrPrice_arr_f[i] = avrPrice;

            // System.out.print("store avrprice" + i + " to avrpricearr, ");
        }

        output_str += avrPrice_arr_f[start - 1];
        // cdSystem.out.print("\n\nput avrpricearr0 to output, ");

        DecimalFormat df = new DecimalFormat("#.##");
        for (int i = start; i < end - 4; i++) {
            output_str += "," + df.format(avrPrice_arr_f[i]);

            // System.out.print("put avrpricearr" + i + " to output, ");
        }

        output_str += "\n";

        try {
            File file = new File("output.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(output_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double standardDeviationCalculator(StockInfo[] stockInfo_clsArr, String name, int start, int end) {
        float[] price_arr_f = new float[priceNum];
        float priceSum_f = 0;
        double avrPrice_d;
        double priceDiffSqrSum_d = 0.0;
        double s_d;
        int n = end - start + 1;

        for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < stockNum; idxOfStockInfoClsArr++) {

            if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName != null
                    && stockInfo_clsArr[idxOfStockInfoClsArr].stockName.equals(name)) {

                for (int idxOfStockPrice = 0; idxOfStockPrice < priceNum; idxOfStockPrice++) {
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
        s_d = Double.parseDouble(bd.setScale(2, RoundingMode.HALF_UP).toString());
        return s_d;
    }

    public static void task2(StockInfo[] stockInfo_clsArr, String name, int start, int end) {

        String output_str = name + "," + start + "," + end + "\n";

        DecimalFormat df = new DecimalFormat("#.##");
        output_str += df.format(standardDeviationCalculator(stockInfo_clsArr, name, start, end));

        output_str += "\n";

        try {
            File file = new File("output.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(output_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void task3(StockInfo[] stockInfo_clsArr, int start, int end) {

        String output_str = "";
        ArrayList<SdInfo> sdInfoList = new ArrayList<>();

        for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < stockNum; idxOfStockInfoClsArr++) {

            if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName != null) {

                SdInfo sdInfo = new SdInfo();

                sdInfo.stockName = stockInfo_clsArr[idxOfStockInfoClsArr].stockName;
                sdInfo.s = standardDeviationCalculator(stockInfo_clsArr, sdInfo.stockName, start, end);

                sdInfoList.add(sdInfo);
            }
        }

        sdInfoList.sort((a, b) -> Double.compare(b.s, a.s));

        for (int i = 0; i < 3; i++) {
            output_str += sdInfoList.get(i).stockName + ",";
        }
        output_str += start + "," + end + "\n";

        for (int i = 0; i < 3; i++) {

            DecimalFormat df = new DecimalFormat("#.##");

            output_str += df.format(sdInfoList.get(i).s) + ",";

        }
        output_str = output_str.substring(0, output_str.length() - 1) + "\n";

        try {
            File file = new File("output.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(output_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void task4(StockInfo[] stockInfo_clsArr, String name, int start, int end) {

        String output_str = name + "," + start + "," + end + "\n";

        double[] price_arr_d = new double[priceNum];
        double priceSum_d = 0.0;
        double avrPrice_d;

        double tSum_d = 0.0;
        double avrT_d;

        double b0, b1, up = 0.0, down = 0.0;

        double start_d = start, end_d = end;

        for (int idxOfStockInfoClsArr = 0; idxOfStockInfoClsArr < stockNum; idxOfStockInfoClsArr++) {

            if (stockInfo_clsArr[idxOfStockInfoClsArr].stockName != null
                    && stockInfo_clsArr[idxOfStockInfoClsArr].stockName.equals(name)) {

                for (int idxOfStockPrice = 0; idxOfStockPrice < priceNum; idxOfStockPrice++) {
                    price_arr_d[idxOfStockPrice] = stockInfo_clsArr[idxOfStockInfoClsArr].stockPrice[idxOfStockPrice];
                }
                break;
            }
        }

        for (int idxOfPrice = start - 1; idxOfPrice < end; idxOfPrice++) {
            priceSum_d += price_arr_d[idxOfPrice];
        }
        avrPrice_d = priceSum_d / (end_d - start_d + 1);

        for (double i = start_d; i <= end_d; i++) {
            tSum_d += i;
        }
        avrT_d = tSum_d / (end_d - start_d + 1);

        for (double idx = start_d - 1; idx < end; idx++) {
            up += (idx + 1 - avrT_d) * (price_arr_d[(int) idx] - avrPrice_d);
        }
        for (double idx = start_d - 1; idx < end; idx++) {
            down += Math.pow(idx + 1 - avrT_d, 2);
        }
        BigDecimal bd = new BigDecimal(up / down);
        b1 = Double.parseDouble(bd.setScale(2, RoundingMode.HALF_UP).toString());

        BigDecimal bd2 = new BigDecimal(avrPrice_d - (up / down) * avrT_d);
        b0 = Double.parseDouble(bd2.setScale(2, RoundingMode.HALF_UP).toString());

        DecimalFormat df = new DecimalFormat("#.##");
        output_str += df.format(b1) + "," + df.format(b0);

        output_str += "\n";

        try {
            File file = new File("output.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(output_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String _input_mode_str = args[0];// store mode
        StockInfo[] _StockInfo_clsArr = new StockInfo[stockNum];
        for (int i = 0; i < stockNum; i++) {
            _StockInfo_clsArr[i] = new StockInfo();
        }

        if (_input_mode_str.equals("0") && args.length == 1) { // mode = 0

            try {
                mode0(parser());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (_input_mode_str.equals("1") && args.length > 1) { // mode = 1

            _StockInfo_clsArr = storeToDataBase();

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

class SdInfo {
    String stockName;
    double s;
}
