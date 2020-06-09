package Graph_1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class task3 {
    public static class Pair implements Comparable<Pair> {
        int a;
        int b;
        int c;

        Pair(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public int compareTo(Pair o) {
            return Long.compare(this.c, o.c);
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

    public static int dfs(int v, int color[]) {
        return (v != color[v] ? (color[v] = dfs(color[v], color)) : v);
    }

    public static void search(int a, int b, int color[]) {
        a = dfs(a, color);
        b = dfs(b, color);
        if (a != b) {
            color[a] = b;
        }
    }

    public static void main(String args[]) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        Pair[] g = new Pair[m];
        int ar[] = new int[n];
        for (int i = 0; i < n; i++) {
            ar[i] = i;
        }
        int a, b, c;
        for (int i = 0; i < m; i++) {
            a = in.nextInt() - 1;
            b = in.nextInt() - 1;
            c = in.nextInt();
            Pair x = new Pair(a, b, c);
            g[i] = x;
        }
        Arrays.sort(g);
        long max = 0;
        for (int i = 0; i < m; i++) {
            if (dfs(g[i].a, ar) != dfs(g[i].b, ar)) {
                max = max + g[i].c;
                search(g[i].a, g[i].b, ar);
            }
        }
        out.print(max);
        out.close();
    }
}
