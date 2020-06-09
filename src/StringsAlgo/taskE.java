package StringsAlgo;

import java.io.PrintWriter;
import java.util.Scanner;

public class taskE {

    public static int[] prefix (String s) {
        int[] prefix = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            int k = prefix[i - 1];
            while (k > 0 && s.charAt(i) != s.charAt(k)) {
                k = prefix[k - 1];
            }
            if (s.charAt(i) == s.charAt(k)) {
                k++;
            }
            prefix[i] = k;
        }
        return prefix;
    }

    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        String s = in.nextLine();
        int []p = prefix(s);
        if (s.length() / 2 >= p[s.length() - 1]) {
            out.print(s.length());
        } else {
            out.print(s.length() - p[s.length() - 1]);
        }
        out.close();
        in.close();
    }
}
