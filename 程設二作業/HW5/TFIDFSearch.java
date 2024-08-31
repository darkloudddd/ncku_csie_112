import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

public class TFIDFSearch {

    public static String[] _inputLine_str_arr;
    public static Indexer _idx;

    // 反序列化
    public static void Deserialize(String name) {
        try {
            FileInputStream fis = new FileInputStream(name + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Indexer deserializedIdx = (Indexer) ois.readObject();
            _idx = deserializedIdx;

            /*
             * System.out.println("================反序列化後idx資訊================");
             * System.out.println("====TEXT_NUM_MAX : " + _idx.TEXT_NUM_MAX);
             * System.out.println("====textNum : " + _idx.textNum);
             * System.out.println("====_texts_str_arr[0] : " + _idx._texts_str_arr[0]);
             * System.out.println("====_texts_str_arr[1] : " + _idx._texts_str_arr[1]);
             * System.out.println("====_texts_str_arr[2] : " + _idx._texts_str_arr[2]);
             * System.out.println("====_texts_str_arr[3] : " + _idx._texts_str_arr[3]);
             * System.out.println("====_texts_str_arr[4] : " + _idx._texts_str_arr[4]);
             * System.out.println("====_texts_str_arr[4] : " + _idx._texts_str_arr[5]);
             */

            ois.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
    }

    public static String[] Parser(String[] str) {
        for (int i = 0; i < str.length; i++) {
            if (str[i] == null) {
                break;
            }
            str[i] = str[i].toLowerCase().replaceAll("[^a-z]", " ");
        }
        return str;
    }

    public static void testCaseReader(String testCaseName) {
        try {
            Path testCaseNamePath = Path.of(testCaseName);
            String testCase = Files.readString(testCaseNamePath);
            _inputLine_str_arr = testCase.split("\\r?\\n|\\r");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double TFCal(String word, int num) {
        int count = 0;
        String[] words = _idx._texts_str_arr[num].split("\\s+");
        for (String w : words) {
            if (w.equals(word)) {
                count++;
            }
        }
        return (double) count / (words.length - 1);
    }

    public static double IDFCal(String word) {
        if (_idx.trie.countDocAppear(word) == 0) {
            return (double) 0;
        }
        // System.out.println("總文檔數 : " + _idx.textNum);
        // System.out.println("包含詞彙的文檔數 : " + _idx.trie.countDocAppear(word));
        return Math.log((double) _idx.textNum / _idx.trie.countDocAppear(word));
    }

    public static double TFIDFCal(String word, int num) {
        return TFCal(word, num) * IDFCal(word);
    }

    public static BitSet And(HashSet<String> appearWords) {
        BitSet bs0 = null;

        // int count = 1;
        for (String word : appearWords) {

            TrieNode node = _idx.trie.searchNode(word);
            if (node == null) {
                return new BitSet();
            }
            if (bs0 == null) {
                bs0 = (BitSet) node.appearDoc_bs.clone(); // 建立第一個單詞的 BitSet 副本
            } else {
                bs0.and(node.appearDoc_bs);
                // System.out.println("============And 了 " + count + " 次===============");
                // count++;
                if (bs0.isEmpty()) { // 提前返回空集合
                    return new BitSet();
                }
            }
        }
        // System.out.println("!!!!!!!!!!!!!有幾個文檔!!!!!!!!!!!!!! : " +
        // bs0.cardinality());
        // System.out.println();
        return bs0;
    }

    public static BitSet Or(HashSet<String> appearWords) {
        BitSet bs0 = new BitSet();

        // int count = 1;
        for (String word : appearWords) {
            TrieNode node = _idx.trie.searchNode(word);
            if (node == null) {
                continue;
            }
            bs0.or(node.appearDoc_bs);
            // System.out.println("=============Or 了 " + count + " 次===============");
            // count++;
        }
        // System.out.println("!!!!!!!!!!!!!有幾個文檔!!!!!!!!!!!!!! : " +
        // bs0.cardinality());
        // System.out.println();
        return bs0;
    }

    public static String AnsCal(int n, HashSet<String> appearWords, BitSet bs) {

        HashMap<Integer, Double> hmap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        if (bs == null || bs.isEmpty()) {
            for (int i = 0; i < n; i++) {
                sb.append("-1 ");
            }
            return sb.toString().trim();
        }

        for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1)) {
            if (i == Integer.MAX_VALUE) {
                break;
            }

            // System.out.println("=================== i = " + i);
            double sum = 0.0;
            for (String word : appearWords) {
                sum += TFIDFCal(word, i);
                // System.out.println("這次 TFCal 算出來多少 : " + TFCal(word, i));
                // System.out.println("這次 IDFCal 算出來多少 : " + IDFCal(word));
                // System.out.println("這次 TFIDFCal 算出來多少 : " + TFIDFCal(word, i));
            }
            // if(i<50){
            // System.out.println("hmap put 啥 : i = " + i + ", sum = " + sum);
            // }

            // System.out.println("\n\n");
            hmap.put(i, sum);
        }
        // System.out.println("!!!!!!!!!!!hmap排序前內容!!!!!!!!!!!!!! :\n" + hmap);

        // sort by value
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(hmap.entrySet());

        list.sort((o1, o2) -> {
            int valueComparison = o2.getValue().compareTo(o1.getValue()); // value大到小排序
            if (valueComparison == 0) {
                return o1.getKey().compareTo(o2.getKey()); // value值相同時 key 小到大排序
            }
            return valueComparison;
        });

        int count = 0;
        for (Map.Entry<Integer, Double> entry : list) {
            sb.append(entry.getKey()).append(" ");
            count++;
            if (count == n) {
                break;
            }
        }

        // Append -1 until we reach n elements
        while (count < n) {
            sb.append("-1 ");
            count++;
        }

        return sb.toString().trim();
    }

    public static void main(String[] args) {
        Deserialize(args[0]);

        _idx._texts_str_arr = Parser(_idx._texts_str_arr);
        testCaseReader(args[1]);
        int input_n = Integer.parseInt(_inputLine_str_arr[0]); // 1 <= n <= 1024
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < _inputLine_str_arr.length; i++) {
            if (_inputLine_str_arr[i] == null) {
                break;
            }

            String words[];
            BitSet bs = new BitSet();
            HashSet<String> appearWords;
            // 判斷這行在 AND還是 OR
            if (_inputLine_str_arr[i].contains("AND")) {
                String line = _inputLine_str_arr[i].replace("AND", " ");
                words = line.split("\\s+");
                appearWords = new HashSet<>(Arrays.asList(words));

                /*
                 * System.out.print("=============印出第 " + i + " 行的 query words : ");
                 * for(String word : appearWords){
                 * System.out.print(word + " ");
                 * }
                 * System.out.println();
                 */

                bs = And(appearWords);
            } else if (_inputLine_str_arr[i].contains("OR")) {
                String line = _inputLine_str_arr[i].replace("OR", " ");
                words = line.split("\\s+");
                appearWords = new HashSet<>(Arrays.asList(words));

                /*
                 * System.out.print("=============印出第 " + i + " 行的 query words : ");
                 * for(String word : appearWords){
                 * System.out.print(word + " ");
                 * }
                 * System.out.println();
                 */

                bs = Or(appearWords);
            } else {
                words = _inputLine_str_arr[i].split("\\s+"); // 該行只有一個字
                appearWords = new HashSet<>(Arrays.asList(words));

                /*
                 * System.out.print("=============印出第 " + i + " 行的 query words : ");
                 * for(String word : words){
                 * System.out.print(word + " ");
                 * }
                 * System.out.println();
                 */

                if (_idx.trie.searchNode(_inputLine_str_arr[i]) == null) {
                    bs = null;
                } else {
                    bs = _idx.trie.searchNode(_inputLine_str_arr[i]).appearDoc_bs;
                }

                // System.out.println("!!!!!!!!!!!!!有幾個文檔!!!!!!!!!!!!!! : " + bs.cardinality());
                // System.out.println();
            }
            sb.append(AnsCal(input_n, appearWords, bs) + "\n");
        }
        sb.deleteCharAt(sb.length() - 1);

        try {
            File file = new File("output.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}