package Graph_1;

import java.io.*;
import java.util.*;

public class condensation {

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

    public static class Pair implements Comparable<Pair> {
        int a;
        int b;

        Pair (int a, int b) {
            this.a = a;
            this.b = b;
        }
        @Override
        public int compareTo(Pair o) {
            return Long.compare(o.a, this.a);
        }
    }

    public static List<Pair> ans = new ArrayList<>();
    public static int timer = 0;
    public static int cur = 0;

    private static void dfsa(List<Integer>[] g, int u, int[] used) {
        used[u] = 1;
        for (int v : g[u]) {
            if (used[v] == 0) {
                dfsa(g, v, used);
            }
        }
        ans.add(new Pair(timer++, u));
    }

    private static void dfsb(List<Integer>[] g, int u, int[]c) {
        c[u] = cur;
        for (int v : g[u]) {
            if (c[v] == 0) {
                dfsb(g, v, c);
            }
        }
    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        HashMap<Integer, String> a = new HashMap<>();
        int n = in.nextInt();
        int m = in.nextInt();
        int[] used = new int[n];
        int[] color = new int[n];
        List<Integer>[] g = new ArrayList[n];
        List<Integer>[] edges = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            g[i].add(in.nextInt() - 1);
            edges[i].add(in.nextInt() - 1);
        }
        for (int i = 0; i < n; i++) {
            if (used[i] == 0) {
                dfsa(g, i, used);
            }
        }
        ans.sort(Comparator.comparing(e -> e.a));
        for (int i = ans.size() - 1; i >= 0; i--) {
            if (color[ans.get(i).b] == 0) {
                ++cur;
                dfsb(edges, ans.get(i).b, color);
            }
        }
        TreeSet<Pair> answer = new TreeSet<>();
        for (int v = 0; v < n; v++) {
            for (int u : g[v]) {
                if (color[v] != color[u]) {
                    answer.add(new Pair(Math.min(color[v], color[u]), Math.max(color[u], color[v])));
                }
            }
        }
        out.print(answer.size());
        out.close();
    }
}

