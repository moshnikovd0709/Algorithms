package LabaThird;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class taskA {

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

    static int depth = 0;
    public static int c = 0;

    public static void deep(int z, int[] depth, int r, ArrayList[] neib) {
        depth[z] = r;
        if (neib[z] == null) {
            return;
        }
        for (int i = 0; i < neib[z].size(); i++) {
            deep((int) neib[z].get(i), depth, r + 1, neib);
        }
    }

    public static void count(int z, int[] young, int[] old, ArrayList[] neib){
        young[z] = c;
        old[c] = z;
        c++;
        if (neib[z] == null) {
            return;
        }
        for (int i = 0; i < neib[z].size(); i++) {
            count((int) neib[z].get(i), young, old, neib);
        }
    }

    public static void dfs(int v, boolean u[], int[]d, ArrayList<Integer>[] neib) {
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

    public static int lca (int u, int v, int[] d, int[][] dp, int[] p) {
        if (d[v] > d[u]) {
            int a = v;
            v = u;
            u = a;
        }
        for (int i = 19; i >= 0; i--) {
            if (d[u] - d[v] >= (1<<i)) {
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

    public static void main (String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        ArrayList<Integer>[] neib = new ArrayList[n];
        int []p = new int[n];
        int []old = new int[n];
        int []young = new int[n];
        int [][]dp = new int[n][20];
        int []d = new int[n];
        boolean []b = new boolean[n];
        int r = 0;
        int x;
        for (int i = 0; i < n; i++) {
            x = in.nextInt() - 1;
            if (x == -2) {
                dp[i][0] = p[i] = i;
                r = i;
            } else {
                if (neib[x] == null) {
                    neib[x] = new ArrayList<>();
                }
                neib[x].add(i);
                p[i] = x;
                dp[i][0] = p[i];
            }
        }
        count(r, young, old, neib);
        for (int j = 1; j < dp[0].length; j++) {
            for (int i = 0; i < n; i++) {
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
            }
        }
        dfs(0, b, d, neib);
        deep(r, d, 1, neib);
        int g = in.nextInt();
        int size, y;
        for (int i = 0; i < g; i++) {
            size = in.nextInt();
            int lor[] = new int[size];
            for (int j = 0; j < size; j++){
                y = in.nextInt() - 1;
                lor[j] = young[y];
            }
            Arrays.sort(lor);
            int res = 0;
            res = res + d[old[lor[0]]];
            for (int j = 1; j < size; j++) {
                int son = lor[j];
                int father = lor[j - 1];
                res = res - d[lca(old[son], old[father], d, dp, p)];
                res = res + d[old[son]];
            }
            out.println(res);
        }
        out.close();
    }
}
