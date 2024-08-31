import java.io.Serializable;
import java.util.BitSet;

public class Indexer implements Serializable {
    public static final long serialVersionUID = 1L;

    public final int TEXT_NUM_MAX = 1000000;
    public String[] _texts_str_arr = new String[TEXT_NUM_MAX];
    public int textNum;
    public Trie trie = new Trie();

    public Indexer(String[] _texts_str_arr, int textNum, Trie trie) {
        // this.TEXT_NUM_MAX = TEXT_NUM_MAX;
        for (int i = 0; i < _texts_str_arr.length; i++) {
            this._texts_str_arr[i] = _texts_str_arr[i];
        }

        // System.out.println(textNum);
        this.textNum = textNum;
        // System.out.println(textNum);

        this.trie = trie;
    }
}

class TrieNode implements Serializable {
    public static final long serialVersionUID = 1L;

    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord;
    // HashSet<Integer> appearDoc_hs = new HashSet<>();// 元素不重複
    BitSet appearDoc_bs = new BitSet();
}

class Trie implements Serializable {
    public static final long serialVersionUID = 1L;

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
        // node.appearDoc_hs.add(docId);
        node.appearDoc_bs.set(docId);
        node.isEndOfWord = true;
    }

    public int countDocAppear(String word) {
        TrieNode node = searchNode(word);
        return node == null ? 0 : node.appearDoc_bs.cardinality(); // 設置為true的個數
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
        return node.isEndOfWord ? node : null;
    }
}
