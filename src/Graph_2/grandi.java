package Graph_2;

import java.io.*;
import java.util.*;

public class grandi {

    public static ArrayList<Integer> ans = new ArrayList<>();

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

    public static void dfs (int v, boolean used[], List<List<Integer>> g) {
        used[v] = true;
        for (int u : g.get(v)) {
            if (!used[u])
                dfs(u, used, g);
        }
        ans.add(v);
    }

    public static class Pair {
        int a;
        int c;

        public Pair(int a, int c) {
            this.c = c;
            this.a = a;
        }
    }

    public static void main (String []args) throws IOException {
        FastScanner in = new FastScanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        List<List<Integer>> g = new ArrayList<>();
        int first, second;
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            first = in.nextInt() - 1;
            second = in.nextInt() - 1;
            g.get(first).add(second);
        }
        boolean used[] = new boolean[n];
        for (int i = 0; i < g.size(); i++) {
            if (used[i] == false) {
                dfs(i, used, g);
            }
        }
        int grandi[] = new int[n];
        grandi[ans.get(0)] = 0;
        for (int i = 1; i < ans.size(); i++) {
            Set<Integer> y = new HashSet<>();
            for (int v = 0; v < g.get(ans.get(i)).size(); v++) {
                y.add(grandi[g.get(ans.get(i)).get(v)]);
            }
            int k = 0;
            while (true) {
                if (!y.contains(k)) {
                    break;
                }
                k++;
            }
            grandi[ans.get(i)] = k;
        }
        PrintWriter out = new PrintWriter(new File("game.out"));
        for (int i = 0; i < ans.size(); i++) {
            out.println(ans.get(i));
        }
        out.close();
    }
}
