package StringsAlgo;

import java.io.*;
import java.util.*;

public class z_function {

    public static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        FastScanner(InputStream f) {
            br = new BufferedReader(new InputStreamReader(f));
        }

        String nextLine() {
            try {
                return br.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static int[] z_fun(String s) {
        int[]z = new int[s.length()];
        int left = 0, right = 0;
        for (int i = 1; i < s.length(); i++) {
            z[i] = Math.max(0, Math.min(right - i, z[i - left]));
            while (i + z[i] < s.length() && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }
            if (i + z[i] > right) {
                left = i;
                right = i + z[i];
            }
        }
        return z;
    }

    public static void main (String []args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        String p = in.nextLine();
        String t = in.nextLine();
        String s = p + "#" + t;
        int[] z = z_fun(s);
        List<Integer> a = new ArrayList<>();
        for (int i = p.length() + 1; i <= t.length() + 1; i++) {
            if (z[i] == p.length()) {
                a.add(i);
            }
        }
        out.println(a.size());
        for (int i = 0; i < a.size(); i++) {
            out.print((a.get(i) - p.length()) + " ");
        }
        out.close();
    }
}
