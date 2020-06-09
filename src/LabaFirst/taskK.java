package LabaFirst;

import java.io.*;
import java.util.StringTokenizer;

public class taskK {

    static class FastScanner {
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

    public static boolean flag;

    public static void exit(int x, int l, int r, int value, int tree[]) {
        if (l == r) {
            tree[x] = 0;
        } else {
            int m = (l + r) / 2;
            if (value <= m) {
                exit(2*x, l, Math.min(m, r), value, tree);
            } else {
                exit(2*x + 1, Math.max(m + 1, l), r, value, tree);
            }
            tree[x] = 0;
        }
    }

    public static int enter (int x, int l, int r, int a, int b, int[] tree) {
        int m = (l + r) / 2;
        if (flag | (a > b)) {
            return Integer.MAX_VALUE;
        }
        if (l == r) {
            if (tree[x] == 0) {
                tree[x] = 1;
                flag = true;
                return l;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        int left = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;
        if (tree[2*x] == 0) {
            left = enter(2*x, l, m, a, Math.min(b, m), tree);
        }
        if (tree[2*x + 1] == 0) {
            right = enter(2*x + 1, m + 1, r, Math.max(a, m + 1), b, tree);
        }
        tree[x] = Math.min(tree[2*x], tree[2*x + 1]) ;
        return Math.min(left, right);
    }

    public static void main (String args[]) throws FileNotFoundException {
        FastScanner in = new FastScanner(new File ("parking.in"));
        int n = in.nextInt();
        int m = in.nextInt();
        int tree[] = new int[4 * n];
        int x;
        String s;
        int answer;
        PrintWriter out = new PrintWriter("parking.out");
        for (int i = 0; i < m; i++) {
            s = in.next();
            flag = false;
            if (s.charAt(1) == 'x') {
                x = in.nextInt();
                exit(1, 0, n - 1, x - 1, tree);
            } else {
                x = in.nextInt();
                answer = (enter(1, 0, n - 1, x - 1, n - 1, tree) + 1);
                if (answer > 0) {
                    out.println(answer);
                } else {
                    out.println(enter(1, 0, n - 1, 0, x - 1, tree) + 1);
                }
            }
        }
        out.close();
    }
}
