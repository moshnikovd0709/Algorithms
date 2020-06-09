package LabaFirst;

import java.io.*;
import java.util.StringTokenizer;

public class taskI {

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

    public static int amount = 1;
    public static int answer = 0;
    public static int inf = (1 << 30) - 1 + (1 << 30);

    public static class Pair {
        int s;
        int max_value;
        int r;
        int left;
        int right;

        public Pair(int s, int max_value, int r, int left, int right) {
            this.s = s;
            this.max_value = max_value;
            this.r = r;
            this.left = left;
            this.right = right;
        }
    }

    public static void push(int x, int a, int b, Pair[] t) {
        if (a != b) {
            if (t[x].left == 0) {
                amount++;
                t[x].left = amount;
            } else if (t[x].right == 0) {
                amount++;
                t[x].right = amount;
            }
        }
        if (t[x].r != inf) {
            if (a != b) {
                t[t[x].right].r = t[t[x].left].r = t[x].r;
            }
            t[x].s = t[x].r * (b - a + 1);
            t[x].max_value = Math.max(0, t[x].s);
            t[x].r = inf;
        }
    }

    public static void get(int x, int a, int b, int pos, Pair[] t, int n) {
        push(x, a, b, t);
        if (t[x].max_value <= pos) {
            if (x == 1) {
                answer = n;
                return;
            }
        }
        if (a == b) {
            answer = a - 1;
            return;
        } else {
            int m = (a + b) / 2;
            push(t[x].left, a, m, t);
            push(t[x].right, m + 1, b, t);
            if (t[t[x].left].max_value <= pos) {
                get(t[x].right, m + 1, b, pos - t[t[x].left].s, t, n);
            } else {
                get(t[x].left, a, m, pos, t, n);
            }
        }
    }

    public static void construct(int x, int a, int b, int l, int r, int h, Pair[] t) {
        push(x, a, b, t);
        if (r < a) {
            return;
        }
        if (l > r) {
            return;
        }
        if (b < l) {
            return;
        }
        if (l <= a && b <= r) {
                t[x].r = h;
                push(x, a, b, t);
        } else {
            int m = (b + a) / 2;
            push(t[x].left, a, m, t);
            push(t[x].right, m + 1, b, t);
            construct(t[x].left, a, m, l, r, h, t);
            construct(t[x].right, m + 1, b, l, r, h, t);
            t[x].s = t[t[x].right].s + t[t[x].left].s;
            t[x].max_value = Math.max(t[t[x].left].max_value, t[t[x].left].s + t[t[x].right].max_value);
        }
    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        Pair[] tree = new Pair[100000];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new Pair(0, 0, inf, 0,0);
        }
        String s;
        int a, b, d;
        while(true) {
            s = in.next();
            if (s.charAt(0) == 'E') {
                break;
            } else if (s.charAt(0) == 'Q') {
              a = in.nextInt();
              get(1,1, n, a, tree, n);
              out.println(answer);
            } else if (s.charAt(0) == 'I') {
              a = in.nextInt();
              b = in.nextInt();
              d = in.nextInt();
              construct(1,1, n, a, b, d, tree);
            }
        }
        out.close();
    }
}
