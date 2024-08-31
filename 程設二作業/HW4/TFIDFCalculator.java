import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.lang.StringBuilder;

public class TFIDFCalculator {

    public static final int TEXT_NUM_MAX = 60000; // 文本最大數量

    public static String[] texts_str = new String[TEXT_NUM_MAX];
    public static Text[] textArr_txt = new Text[TEXT_NUM_MAX];
    public static int TEXT_NUM;

    public static String[] targetName;
    public static String[] targetText;

    public static void docReader(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            int idxOflines = 1;

            int idxOftexts = 0;
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {

                sb.append(line.toLowerCase().replaceAll("[^a-z]", " "));

                if (idxOflines % 5 == 0) {
                    texts_str[idxOftexts] = sb.toString();
                    idxOftexts++;
                    sb.delete(0, sb.length());
                }

                idxOflines++;
            }

            TEXT_NUM = (idxOflines - 1) / 5;

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testCaseReader(String testCaseName) {

        try {
            Path testCaseNamePath = Path.of(testCaseName);
            String testCase = Files.readString(testCaseNamePath);
            String[] tc = testCase.split("\\r?\\n|\\r");
            targetName = tc[0].split("\\s+");
            targetText = tc[1].split("\\s+");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Text Parser(String many_words) {

        Text text = new Text();
        StringTokenizer st = new StringTokenizer(many_words, " ");

        while (st.hasMoreTokens()) {
            text.words.add(st.nextToken());
        }

        return text;
    }

    public static double TFCal(String word, int num) {

        int count = 0;
        for (String w : textArr_txt[num].words) {
            if (w.equals(word)) {
                count++;
            }
        }

        // System.out.println("===========TF count:" + count);
        // System.out.println("==========TF how many words:" +
        // textArr_txt[num].words.size());
        // System.out.println("=========TF:" + (double) count /
        // textArr_txt[num].words.size());
        return (double) count / textArr_txt[num].words.size();
    }

    public static double IDFCal(String word, int textNum) {

        int count = 0;
        for (int idxOftextArr = 0; idxOftextArr < TEXT_NUM; idxOftextArr++) {

            if (textArr_txt[idxOftextArr].words.contains(word)) {
                count++;
            }
        }

        // System.out.println(count);
        // System.out.println("===========Idf:" + (double) Math.log((double) TEXT_NUM /
        // count));
        return (double) Math.log((double) TEXT_NUM / count);
    }

    public static double TFIDFCal(String word, int num) {
        return TFCal(word, num) * IDFCal(word, num);
    }

    public static void main(String[] args) {

        docReader(args[0]);
        testCaseReader(args[1]);
        System.out.println(TEXT_NUM);

        for (int idxOftextArr = 0; idxOftextArr < textArr_txt.length; idxOftextArr++) {

            if (texts_str[idxOftextArr] == null || texts_str[idxOftextArr].equals("")) {
                break;
            }

            textArr_txt[idxOftextArr] = Parser(texts_str[idxOftextArr]);
        }

        String output_str = "";
        output_str += String.format("%.5f", TFIDFCal(targetName[0], Integer.parseInt(targetText[0])));
        for (int n = 1; n < targetName.length; n++) {
            output_str += " " + String.format("%.5f", TFIDFCal(targetName[n], Integer.parseInt(targetText[n])));
        }

        try {
            File file = new File("output.txt");
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
}

class Text {
    ArrayList<String> words = new ArrayList<>();
}