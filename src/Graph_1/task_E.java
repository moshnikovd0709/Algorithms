package Graph_1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class task_E {

    public static int timer, tin[], fup[];
    public static int max = 0;

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

    public static void dfs (int v, int used[], List<Integer>[] g, int p[]) {
        used[v] = 1;
        tin[v] = fup[v] = timer++;
        for (int to : g[v]) {
            if (used[to] == 0){
                p[to] = v;
                dfs(to, used, g, p);
                fup[v] = Math.min(fup[v], fup[to]);
            } else if (to != p[v]) {
                fup[v] = Math.min(fup[v], tin[to]);
            }
        }
    }

    public static void paint(int u, List<Integer>[] g, int cur, int[] color) {
        color[u] = cur;
        for (int v : g[u]) {
            if (color[v] == 0) {
                paint(v, g, fup[v] > tin[u] ? ++max : cur, color);
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
        int used[] = new int[n];
        int first, second;
        int p[] = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = -1;
        }
        List<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int j = 0; j < m; j++) {
            first = in.nextInt() - 1;
            second = in.nextInt() - 1;
            g[first].add(second);
            g[second].add(first);
        }
        timer = 0;
        for (int i = 0; i < n; i++) {
            if (used[i] == 0) {
                dfs(i, used, g, p);
            }
        }
        int[] color = new int[n];
        for (int i = 0; i < n; i++) {
            if (color[i] == 0) {
                paint(i, g, ++max, color);
            }
        }
        out.println(max);
        for (int i = 0; i < color.length; i++) {
            out.print(color[i] + " ");
        }
        out.close();
    }
}
