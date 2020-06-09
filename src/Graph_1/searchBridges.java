package Graph_1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class searchBridges {

    public static ArrayList<Integer> ans = new ArrayList<>();
    public static int timer, tin[], fup[];

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

    public static class Pair {
        int a;
        int b;

        Pair (int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    public static void dfs (int v, int used[], List<Pair>[] g, int p[]) {
        used[v] = 1;
        tin[v] = fup[v] = timer++;
        for (Pair to : g[v]) {
            if (to.a == p[v]) {
                continue;
            }
            if (used[to.a] == 1) {
                fup[v] = Math.min(fup[v], tin[to.a]);
            } else {
                p[to.a] = v;
                dfs(to.a, used, g, p);
                fup[v] = Math.min(fup[v], fup[to.a]);
                if (fup[to.a] > tin[v]) {
                    ans.add(to.b);
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
        int used[] = new int[n];
        int first, second;
        int p[] = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = -1;
        }
        List<Pair>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int j = 0; j < m; j++) {
            first = in.nextInt() - 1;
            second = in.nextInt() - 1;
            Pair a = new Pair(second, j);
            g[first].add(a);
            a = new Pair(first, j);
            g[second].add(a);
        }
        timer = 0;
        for (int i = 0; i < n; i++) {
            if (used[i] == 0) {
                dfs(i, used, g, p);
            }
        }
        out.println(ans.size());
        int[] x = new int[ans.size()];
        int k = 0;
        for (int i = 0; i < ans.size(); i++) {
            x[k] = ans.get(i) + 1;
            k++;
        }
        Arrays.sort(x);
        for (int i = 0; i < ans.size(); i++) {
            out.print(x[i] + " ");
        }
        out.close();
    }
}
