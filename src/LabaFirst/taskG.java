package LabaFirst;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class taskG {

    public static class Pair {
        int set;
        int add;

        public Pair(int set, int add) {
            this.set = set;
            this.add = add;
        }
    }

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

    public static int MAX_V = 1000000002;
    public static int N = 1000000;
    public static int MAX_C = 200000;
    public static int maxX = -1;
    public static int maxV = -1;
    public static int maxY = -1;
    public static Pair p[] = new Pair[4 * N];
    public static Pair z[] = new Pair[4 * N];

    public static void push(int v, int l, int r) {
        if (p[v].set != MAX_V) {
            z[v].set = p[v].set;
        }
        z[v].set += p[v].add;
        if (z[2 * v].add == -1) {
            z[2 * v].add = l;
        }
        if (z[2 * v + 1].add == -1) {
            z[2 * v].add = (r + l) / 2;
        }
        if (l == r) {
            p[v].set = MAX_V;
            p[v].add = 0;
            return;
        }
        if (p[v].set != MAX_V) {
            p[2 * v].set = p[2 * v + 1].set = p[v].set;
            p[2 * v + 1].add = p[2 * v].add = 0;
        }
        p[2 * v].add += p[v].add;
        p[2 * v + 1].add += p[v].add;
        p[v].set = MAX_V;
        p[v].add = 0;
    }

    public static void build(int l, int r, int v) {
        if (l == r) {
            z[v].set = 0;
            z[v].add = l;
            return;
        }
        int m = (r + l) / 2;
        build(l, m, 2 * v);
        build(m + 1, r, 2 * v + 1);
        z[v].set = 0;
        z[v].add = z[2 * v].add;
    }

    public static void fadd(int from, int to, int x, int l, int r, int v) {
        push(v, l, r);
        if (to < l || from > r) {
            return;
        }
        if (from <= l && to >= r) {
            p[v].add = x;
            push(v, l, r);
            return;
        }
        int m = (l + r) / 2;
        fadd(from, to, x, l, m, 2 * v);
        fadd(from, to, x, m + 1, r, 2 * v + 1);
        if (z[2 * v].set >= z[2 * v + 1].set) {
            z[v].set = z[2 * v].set;
            z[v].add = z[2 * v].add;
        } else {
            z[v].set = z[2 * v + 1].set;
            z[v].add = z[2 * v + 1].add;
        }
    }

    public static void main (String args[]) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        for (int i = 0; i < 4 * N; i++) {
            p[i] = new Pair(MAX_V, 0);
            z[i] = new Pair(0,0);
        }
        build(0, 2 * MAX_C, 1);
        ArrayList<Pair>[] toAdd = new ArrayList[400001];
        ArrayList<Pair>[] toRemove = new ArrayList[400001];
        int a, b, c, d;
        for (int i = 0; i < n; i++) {
            a = in.nextInt();
            b = in.nextInt();
            c = in.nextInt();
            d = in.nextInt();
            if (toAdd[a + MAX_C] == null) {
                toAdd[a + MAX_C] = new ArrayList<>();
            }
            if (toRemove[c + MAX_C] == null) {
                toRemove[c + MAX_C] = new ArrayList<>();
            }
            toAdd[a + MAX_C].add(new Pair(b + MAX_C, d + MAX_C));
            toRemove[c + MAX_C].add(new Pair(b + MAX_C, d + MAX_C));
        }
        for (int i = -MAX_C; i < MAX_C + 1; i++) {
            if (toAdd[i + MAX_C] != null) {
                for (Pair pa : toAdd[i + MAX_C]) {

                    fadd(pa.set, pa.add, 1, 0, 2 * MAX_C, 1);
                }
            }
            if (z[1].set > maxV) {
                maxV = z[1].set;
                maxX = i;
                maxY = z[1].add;
            }
            if (toRemove[i + MAX_C] != null) {
                for (Pair pa : toRemove[i + MAX_C]) {
                    fadd(pa.set, pa.add, -1, 0, 2 * MAX_C, 1);
                }
            }
        }
        out.println(maxV);
        out.println(maxX + " " + (maxY - MAX_C));
    out.close();
    }
}
