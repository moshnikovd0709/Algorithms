package Graph_1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class topologicalSorting {

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

    public static boolean flag = true;

    public static void dfs (int v, int used[], List<List<Integer>> g) {
        used[v] = 1;
        for (int u : g.get(v)) {
            if (used[u] == 0) {
                dfs(u, used, g);
            } else if (used[u] == 1){
                flag = false;
                return;
            }
        }
        used[v] = 2;
        ans.add(v);
    }
    public static void main (String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        int first, second;
        boolean b[] = new boolean[n];
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }
        for (int j = 0; j < m; j++) {
            first = in.nextInt();
            second = in.nextInt();
            g.get(first - 1).add(second - 1);
        }
        int used[] = new int[n];
        for (int i = 0; i < n; i++) {
            if (used[i] == 0) {
                dfs(i, used, g);
            }
            if (ans.size() == n) {
                break;
            }
        }
        if (!flag) {
            out.print(-1);
        } else {
            for (int i = ans.size() - 1; i >= 0; i--) {
                out.print((ans.get(i) + 1) + " ");
            }
        }
        out.close();
    }
}
