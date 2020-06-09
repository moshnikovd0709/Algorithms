package flow;

import java.io.*;
import java.util.*;

public class Cut {

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

    private static int dfs(int u, int t, int f, int[] edge, int[] d, List<Edge>[] g, int k) {
        if (u == t) {
            return f;
        }
        for (int i = edge[u]; i < g[u].size(); i++) {
            Edge e = g[u].get(i);
            edge[u] = i;
            if (d[e.v] == d[u] + 1 && ((long) e.f + (1 << k) <= e.c)) {
                int delta = dfs(e.v, t, Math.min(f, e.c - e.f), edge, d, g, k);
                if (delta > 0) {
                    e.f += delta;
                    e.back.f -= delta;
                    return delta;
                }
            }
        }
        return 0;
    }

    private static int bfs(int s, int t, int[] d, List<Edge>[] g, int k) {
        Arrays.fill(d, -1);
        d[s] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.remove();
            for (Edge e : g[u]) {
                if (d[e.v] < 0 && ((long) e.f + (1 << k) <= e.c)) {
                    d[e.v] = d[u] + 1;
                    q.add(e.v);
                }
            }
        }
        return d[t];
    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        List<Edge>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            int c = in.nextInt();
            Edge f = new Edge(b, c);
            Edge s = new Edge(a, c);
            f.back = s;
            s.back = f;
            g[a].add(f);
            g[b].add(s);
        }
        int[] d = new int[n];
        for (int k = 30; k >= 0; k--) {
            while (bfs(0, n - 1, d, g, k) >= 0) {
                while (true) {
                    if (dfs(0, n - 1, Integer.MAX_VALUE, new int[n], d, g, k) == 0) {
                        break;
                    }
                }
            }
        }
        List<Integer> minCut = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (d[i] != -1) {
                minCut.add(i);
            }
        }
        System.out.println(minCut.size());
        for (int u : minCut) {
            System.out.print(u + 1 + " ");
        }
    }

    private static class Edge {
        int v;
        Edge back;
        int c;
        int f;

        Edge(int v, int c) {
            this.v = v;
            this.c = c;
        }
    }
}
