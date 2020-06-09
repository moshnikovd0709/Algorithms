package flow;

import java.io.*;
import java.util.StringTokenizer;

public class Flow {

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

        private static int dfs(int u, int t, int cap, int[][] capacity, boolean[] used) {
            if (u == t) {
                return cap;
            }
            used[u] = true;
            int n = capacity.length;
            for (int v = 0; v < n; v++) {
                if (!used[v] && capacity[u][v] > 0) {
                    int delta = dfs(v, t, Math.min(cap, capacity[u][v]), capacity, used);
                    capacity[v][u] += delta;
                    capacity[u][v] -= delta;
                    if (delta > 0) {
                        return delta;
                    }
                }
            }
            return 0;
        }

        public static void main(String[] args) {
            FastScanner in = new FastScanner(System.in);
            int n = in.nextInt();
            int m = in.nextInt();
            int[][] capacity = new int[n][n];
            for (int i = 0; i < m; i++) {
                int a = in.nextInt() - 1;
                int b = in.nextInt() - 1;
                int c = in.nextInt();
                capacity[a][b] = c;
            }

            int maxFlow = 0;
            int delta;
            do {
                delta = dfs(0, n - 1, Integer.MAX_VALUE, capacity, new boolean[n]);
                maxFlow += delta;
            } while (delta != 0);

            System.out.println(maxFlow);
        }

    }
