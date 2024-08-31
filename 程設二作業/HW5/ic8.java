import java.util.Arrays;
import java.util.HashSet;
import java.util.BitSet;

public class ic8 {

    public static void PrintWords(String[] words) {
        for (String word : words) {
            System.out.println(word);
        }
    }

    public static void main(String[] args) {

        String str = "like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like AND like";
        String[] words = str.split("\\s+AND\\s+|\\s+OR\\s+");
        HashSet<String> hs = new HashSet<>(Arrays.asList(words));
        BitSet bs1 = new BitSet();
        BitSet bs2 = new BitSet();

        for (int i = 0; i < 1000; i++) {
            bs1.set(i);
        }
        for (int i = 1001; i < 3000; i++) {
            bs2.set(i);
            bs1.or(bs2);
        }
        System.out.println(bs1);

        /*
         * for (String word : hs) {
         * System.out.println(word);
         * }
         * 
         * System.out.println("\n\n\n\n\n");
         * 
         * for (String word : words) {
         * System.out.println(word);
         * }
         */
    }
}
