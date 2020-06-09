package Graph_1;

import java.io.*;
import java.util.*;

public class taskC {
    public static TreeSet<Integer> ans = new TreeSet<>();
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

    public static void dfs (int v, int used[], List<Integer>[] g, int p[]) {
        used[v] = 1;
        tin[v] = fup[v] = timer++;
        int ch = 0;
        for (int u : g[v]) {
            if (used[u] == 0) {
                ch++;
                p[u] = v;
                dfs(u, used, g, p);
                fup[v] = Math.min(fup[u], fup[v]);
                if (p[v] != -1 && fup[u] >= tin[v]) {
                    ans.add(v);
                }
                if (p[v] == -1 && ch > 1) {
                    ans.add(v);
                }
            } else if (u != p[v]) {
                fup[v] = Math.min(fup[v], tin[u]);
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
        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = -1;
        }
        for (int i = 0; i < n; i++) {
            if (used[i] == 0) {
                dfs(i, used, g, p);
            }
        }
        int x[] = new int[ans.size()];
        int k = 0;
        Iterator<Integer> itr = ans.iterator();
        while (itr.hasNext()) {
            x[k] = itr.next();
            k++;
        }
        Arrays.sort(x);
        out.println(x.length);
        for (int  i = 0; i < x.length; i++) {
            out.print((x[i] + 1) + " ");
        }
        out.close();
    }
}
