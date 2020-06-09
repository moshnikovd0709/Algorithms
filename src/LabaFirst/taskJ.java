package LabaFirst;

import java.io.*;
import java.util.StringTokenizer;

public class taskJ {

    public static Pair max = new Pair(Long.MAX_VALUE, 0, 0);

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

    public static class Pair {
        long height;
        int number;
        long min;

        public Pair(long height, int number, long min) {
            this.height = height;
            this.number = number;
            this.min = min;
         }
    }

    public static void build(int v, int l, int r, Pair[] tree) {
        if (l == r) {
            tree[v].number = l + 1;
        } else {
            int m = l + ((r - l) / 2);
            build(2*v, l, m, tree);
            build(2*v + 1, m + 1, r, tree);
            tree[v].number = Math.min(tree[2*v].number, tree[2*v + 1].number);
        }
    }

    public static void push(int left, int right, int x, Pair[] tree) {
        if (tree[x].min > tree[x].height) {
            tree[x].height = tree[x].min;
            if (left != right) {
                tree[2*x].min = tree[x].min;
                tree[2*x + 1].min = tree[x].min;
            }
            tree[x].min = 0;
        }
    }

    public static void update(int left, int right, int x, int a, int b, long z, Pair[] tree) {
        push(left, right, x, tree);
        if (b < a) {
            return;
        }
        if (left == a && right == b) {
            if (tree[x].height < z) {
                tree[x].min = z;
                push(left, right, x, tree);
            }
            return;
        }
        int m =  left + ((right - left) / 2);
        push(left, m, 2 * x, tree);
        push(m + 1, right, 2 * x + 1, tree);
        update(left, m, 2 * x, a, Math.min(m, b), z, tree);
        update(m + 1, right, 2 * x + 1, Math.max(m + 1, a), b, z, tree);
        if (tree[2 * x].height <= tree[2 * x + 1].height) {
            tree[x] = tree[2 * x];
        } else {
            tree[x] = tree[2 * x + 1];
        }
    }

    public static Pair get(int l, int r, int v, int x, int y, Pair[] tree) {
        push(l, r, v, tree);
        if (x > y) {
            return max;
        }
        if (l == x && r == y) {
            return tree[v];
        }
        int m =  l + ((r - l) / 2);
        push(l, m, 2 * v, tree);
        push(m + 1, r, 2 * v + 1, tree);
        Pair a = get(l, m, v * 2, x, Math.min(y, m), tree);
        Pair b = get(m + 1, r, v * 2 + 1, Math.max(x, m + 1), y, tree);
        if (a.height <= b.height) {
            return a;
        } else {
            return b;
        }
    }

    public static void main (String args[]) {
        FastScanner in = new FastScanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        Pair[] tree = new Pair[4*n];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new Pair(0, 0 ,0);
        }
        build(1, 0, n - 1, tree);
        int a;
        int b;
        int c;
        String s;
        Pair answer;
        for (int i = 0; i < m; i++) {
            s = in.next();
            if (s.charAt(0) == 'd') {
                a = in.nextInt();
                b = in.nextInt();
                c = in.nextInt();
                update(0, n - 1, 1, a - 1, b - 1, c, tree);
            } else {
                a = in.nextInt();
                b = in.nextInt();
                answer = get( 0, n - 1, 1,a - 1, b - 1, tree);
                System.out.println(answer.height + " " + answer.number);
            }
        }
        return;
    }
}
