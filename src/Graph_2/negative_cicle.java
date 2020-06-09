package Graph_2;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class negative_cicle {

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

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int g[][] = new int[n][n];
        long dp[] = new long[n];
        int parent[] = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
            dp[i] = Long.MAX_VALUE;
        }
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = in.nextInt();
                if (g[i][j] == 100000) {
                    g[i][j] = Integer.MAX_VALUE - 10000;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++){
                for (int k = 0; k < n; k++) {
                    if (g[j][k] != Integer.MAX_VALUE - 10000) {
                        if (dp[k] > dp[j] + g[j][k]) {
                            dp[k] = dp[j] + g[j][k];
                            parent[k] = j;
                        }
                    }
                }
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[j] > dp[i] + g[i][j] && g[i][j] != Integer.MAX_VALUE - 10000) {
                    out.println("YES");
                    for (int k = 0; k < 2 * n; k++) {
                        j = parent[j];
                    }
                    i = j;
                    while (i != parent[j]) {
                        ans.add(j);
                        j = parent[j];
                    }
                    ans.add(j);
                    out.println(ans.size());
                    for (int y = 0; y < ans.size(); y++) {
                        out.print(ans.get(y) + 1 + " ");
                    }
                    out.close();
                    return;
                }
            }
        }
        out.println("NO");
    }
}
