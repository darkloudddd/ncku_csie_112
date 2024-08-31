import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TFIDFCalculator {

    private static final int TEXT_NUM_MAX = 60000;
    private static String[] _texts_str = new String[TEXT_NUM_MAX];
    private static int textNumber;// 實際文本數量

    // store input
    private static String[] targetName;
    private static String[] targetText;

    private static Trie trie = new Trie();

    public static void docReader(String fileName) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int idxOflines = 1;
            int idxOftexts = 0;
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line.toLowerCase().replaceAll("[^a-z]", " "));

                if (idxOflines % 5 == 0) {
                    _texts_str[idxOftexts] = sb.toString();

                    StringTokenizer st = new StringTokenizer(_texts_str[idxOftexts], " ");
                    while (st.hasMoreTokens()) {
                        trie.insert(st.nextToken(), idxOftexts);
                    }

                    idxOftexts++;
                    sb.delete(0, sb.length());
                }

                idxOflines++;
            }
            textNumber = (idxOflines - 1) / 5;
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

    public static double TFCal(String word, int num) {
        int count = 0;
        String[] words = _texts_str[num].split("\\s+");
        for (String w : words) {
            if (w.equals(word)) {
                count++;
            }
        }

        // System.out.println("========TF count:" + count);
        // System.out.println("=======TF how many words:" + words.length);
        // System.out.println("============TF:" + (double) count / words.length);
        return (double) count / (words.length - 1);////// split的問題
    }

    public static double IDFCal(String word) {
        // System.out.println("============IDF:" + (double) Math.log((double) textNumber
        // / trie.countDocAppear(word)));
        return (double) Math.log((double) textNumber / trie.countDocAppear(word));
    }

    public static double TFIDFCal(String word, int num) {
        return TFCal(word, num) * IDFCal(word);
    }

    public static void main(String[] args) {
        docReader(args[0]);
        testCaseReader(args[1]);
        // System.out.println("Input:" + args[0] + "\n" + args[1]);
        // System.out.println(textNumber);

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

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord;
    ArrayList<Integer> appearDoc = new ArrayList<>();
}

class Trie {
    private TrieNode root = new TrieNode();

    public void insert(String word, int docId) {
        TrieNode node = root;

        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        if (!node.appearDoc.contains(docId)) {
            node.appearDoc.add(docId);
        }
        node.isEndOfWord = true;
        // System.out.println("=============insert word:" + word + "
        // sucessful!!=============");
    }

    public int countDocAppear(String word) {
        TrieNode node = searchNode(word);
        // System.out.println("============countDocAppear============ node = " +
        // node.appearDoc.size() + " !!!!!");
        return node == null ? 0 : node.appearDoc.size();
    }

    public TrieNode searchNode(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                return null;
            }
            node = node.children[idx];
        }

        if (node.isEndOfWord) {
            // System.out.println("==========searchNode================ " + word + " is
            // found!!!!!!");
            return node;
        } else {
            return null;
        }

    }
}