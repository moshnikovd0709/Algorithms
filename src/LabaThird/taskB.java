package LabaThird;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class taskB {

    static int depth = 0;

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

    public static void dfs(int v, boolean u[], int[] d, ArrayList<Integer>[] neib) {
        if (u[v]) {
            return;
        }
        u[v] = true;
        d[v] = depth;
        depth++;
        if (neib[v] != null) {
            for (int i : neib[v]) {
                dfs(i, u, d, neib);
            }
        }
        depth--;
    }

    public static int lca(int u, int v, int[] d, int[][] dp, int[] p) {
        if (d[v] > d[u]) {
            int a = v;
            v = u;
            u = a;
        }
        for (int i = 19; i >= 0; i--) {
            if (d[u] - d[v] >= (1 << i)) {
                u = dp[u][i];
            }
        }
        if (v == u) {
            return v;
        }
        for (int i = 19; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        return p[v];
    }

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner in = new FastScanner(new File("minonpath.in"));
        PrintWriter out = new PrintWriter(new File("minonpath.out"));
        int n = in.nextInt();
        ArrayList<Integer>[] neib = new ArrayList[n];
        int[][] cost = new int[n][20];
        int[] p = new int[n];
        int[] d = new int[n];
        int[][] dp = new int[n][20];
        boolean[] b = new boolean[n];
        for (int i = 1; i < n; i++) {
            p[i] = in.nextInt() - 1;
            cost[i][0] = in.nextInt();
            dp[i][0] = p[i];
            if (neib[p[i]] == null) {
                neib[p[i]] = new ArrayList<>();
            }
            neib[p[i]].add(i);
        }
        for (int j = 1; j < dp[0].length; j++) {
            for (int i = 0; i < n; i++) {
                if (dp[i][j - 1] == 0) {
                    continue;
                }
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
                cost[i][j] = Math.min(cost[i][j - 1], cost[dp[i][j - 1]][j - 1]);
            }
        }
        dfs(0, b, d, neib);
        int m = in.nextInt();
        int u, v, x;
        for (int i = 0; i < m; i++) {
            u = in.nextInt() - 1;
            v = in.nextInt() - 1;
            x = lca(u, v, d, dp, p);
            int min = Integer.MAX_VALUE;
            for (int j = 19; j >= 0; j--) {
                if (d[u] - d[x] >= (1 << j)) {
                    min = Math.min(min, cost[u][j]);
                    u = dp[u][j];
                }
            }
            for (int j = 19; j >= 0; j--) {
                if (d[v] - d[x] >= (1 << j)) {
                    min = Math.min(min, cost[v][j]);
                    v = dp[v][j];
                }
            }
            out.println(min);
        }
        out.close();
    }
}