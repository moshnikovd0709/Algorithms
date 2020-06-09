package Graph_1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class task_D {
    public static int timer, tin[], fup[];
    public static int max = 0;

    public static class Pair {
        int a;
        int b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

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

    public static void dfs (int v, int used[], List<Pair>[] g, int p, int[] m) {
        used[v] = 1;
        tin[v] = fup[v] = timer++;
        for (Pair to : g[v]) {
            if (used[to.a] == 0){
                dfs(to.a, used, g, to.b, m);
            }
            if (to.b != p) {
                fup[v] = Math.min(tin[v], tin[to.a]);
            }
        }
        if (fup[v] == tin[v] && p != -1) {
            m[p] = 1;
        }
    }

    public static void paint(int u, List<Pair>[] g, int cur, int[] c, int[] m) {
        c[u] = cur;
        for (Pair v : g[u]) {
            if (c[v.a] == 0) {
                if (m[v.b] == 1) {
                    paint(v.a, g, ++cur, c, m);
                } else {
                    paint(v.a, g, cur, c, m);
                }
            }
        }
    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        tin = new int[n];
        fup = new int[n];
        for (int i = 0; i < n; i++) {
            fup[i] = tin[i] = 100000000;
        }
        int used[] = new int[n];
        int first, second;
        int p = -1;
        int mar[] = new int[m];
        List<Pair>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int j = 0; j < m; j++) {
            first = in.nextInt() - 1;
            second = in.nextInt() - 1;
            g[first].add(new Pair(second, j));
            g[second].add(new Pair(first, j));
        }
        timer = 0;
        for (int i = 0; i < n; i++) {
            if (used[i] == 0) {
                dfs(i, used, g, p, mar);
            }
        }
        int[] color = new int[n];
        for (int i = 0; i < n; i++) {
            if (color[i] == 0) {
                paint(i, g, ++max, color, mar);
            }
        }
        out.println(max);
        for (int i : color) {
            out.print(i + " ");
        }
        out.close();
    }
}
