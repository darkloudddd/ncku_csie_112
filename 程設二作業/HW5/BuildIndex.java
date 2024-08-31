import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

// e.x.  courpus0.txt ---> courpus0.ser
// 序列化 idx
public class BuildIndex {

    public static final int TEXT_NUM_MAX = 1000000;
    public static String[] _texts_str_arr = new String[TEXT_NUM_MAX];
    public static int textNum;
    public static Indexer _idx;

    public static Trie corpusReader(String fileName) {
        Trie trie = new Trie();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int idxOflines = 1;
            int idxOftexts = 0;
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line.toLowerCase().replaceAll("[^a-z]", " "));

                if (idxOflines % 5 == 0) {
                    String text = sb.toString();
                    _texts_str_arr[idxOftexts] = text;

                    String[] words = text.split("\\s+");
                    for (String word : words) {
                        trie.insert(word, idxOftexts);
                    }

                    idxOftexts++;
                    sb.setLength(0);
                }

                idxOflines++;
            }
            textNum = (idxOflines - 1) / 5;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trie;
    }

    public static void main(String args[]) {

        String input_file_name_num = args[0].substring(args[0].lastIndexOf(".txt") - 1, args[0].indexOf(".txt"));
        String input_file_name = "corpus" + input_file_name_num;
        Trie trie = corpusReader(args[0]);

        /*
         * System.out.println("================序列化前idx資訊================");
         * System.out.println("====TEXT_NUM_MAX : " + TEXT_NUM_MAX);
         * System.out.println("====textNum : " + textNum);
         * System.out.println("====_texts_str_arr[0] : " + _texts_str_arr[0]);
         * System.out.println("====_texts_str_arr[1] : " + _texts_str_arr[1]);
         * System.out.println("====_texts_str_arr[2] : " + _texts_str_arr[2]);
         * System.out.println("====_texts_str_arr[3] : " + _texts_str_arr[3]);
         * System.out.println("====_texts_str_arr[4] : " + _texts_str_arr[4]);
         */
        _idx = new Indexer(_texts_str_arr, textNum, trie);

        /*
         * System.out.println("================付值後序列化前idx資訊================");
         * System.out.println("====TEXT_NUM_MAX : " + _idx.TEXT_NUM_MAX);
         * System.out.println("====textNum : " + _idx.textNum);
         * System.out.println("====_texts_str_arr[0] : " + _idx._texts_str_arr[0]);
         * System.out.println("====_texts_str_arr[1] : " + _idx._texts_str_arr[1]);
         * System.out.println("====_texts_str_arr[2] : " + _idx._texts_str_arr[2]);
         * System.out.println("====_texts_str_arr[3] : " + _idx._texts_str_arr[3]);
         * System.out.println("====_texts_str_arr[4] : " + _idx._texts_str_arr[4]);
         * System.out.println("====_texts_str_arr[4] : " + _idx._texts_str_arr[5]);
         */
        try {
            FileOutputStream fos = new FileOutputStream(input_file_name + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_idx);
            // System.out.println("!!!!!!!!!!!產生index成功!!!!!!!!!!!!");

            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
