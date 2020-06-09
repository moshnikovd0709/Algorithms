package Graph_1;

import java.io.*;
import java.util.StringTokenizer;

public class task_G {
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

    public static double solve(int[] ab, int[] or, int n) {
        int[] used = new int[n];
        double[] m = new double[n];
        m[0] = 0;
        for (int i = 1; i < n; i++) {
            m[i] = Double.MAX_VALUE;
        }
        double ans = 0;
        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (used[j] == 0 && (u == -1 || m[j] < m[u])) {
                    u = j;
                }
            }
            used[u] = 1;
            ans += m[u];
            for (int v = 0; v < n; v++) {
                int dx = Math.abs(ab[u] - ab[v]);
                int dy = Math.abs(or[u] - or[v]);
                double val = Math.sqrt(dx * dx + dy * dy);
                m[v] = Math.min(m[v], val);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] ab = new int[n];
        int[] or = new int[n];
        for (int i = 0; i < n; i++) {
            ab[i] = in.nextInt();
            or[i] = in.nextInt();
        }
        double ans;
        ans = solve(ab, or, n);
        out.println(ans);
        out.close();
    }
}
