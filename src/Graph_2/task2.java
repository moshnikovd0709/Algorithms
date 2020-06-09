package Graph_2;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class task2 {
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
        int c;

        public Pair(int a, int c) {
            this.c = c;
            this.a = a;
        }
    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList[] g = new ArrayList[n];
        int first, second, cost;
        for (int i = 0; i < m; i++) {
            first = in.nextInt() - 1;
            second = in.nextInt() - 1;
            cost = in.nextInt();
            if (g[first] == null) {
                g[first] = new ArrayList<Pair>();
            }
            g[first].add(new Pair(second, cost));
            if (g[second] == null) {
                g[second] = new ArrayList<Pair>();
            }
            g[second].add(new Pair(first, cost));
        }
        int []d = new int[n];
        d[0] = 0;
        for (int i = 1; i < n; i++) {
            d[i] = Integer.MAX_VALUE;
        }
        boolean u[] = new boolean[n];
        TreeSet<Integer> queue = new TreeSet<>((o1, o2) -> {
            if (d[o1] > d[o2]) {
                return 1;
            } else if (d[o1] < d[o2]) {
                return -1;
            } else {
                return Integer.compare(o1, o2);
            }
        });
        for (int i = 0; i < n; i++) {
            queue.add(i);
        }
        for (int i = 0; i < n; i++) {
            int v = queue.pollFirst();
            if (d[v] == Integer.MAX_VALUE) {
                break;
            }
            u[v] = true;
            int to, length;
            if (g[v] != null) {
                for (int j = 0; j < g[v].size(); j++) {
                    Pair p = (Pair) g[v].get(j);
                    to = p.a;
                    length = p.c;
                    if (d[v] + length < d[to]) {
                        queue.remove(to);
                        d[to] = d[v] + length;
                        queue.add(to);
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            out.print(d[i] + " ");
        }
        out.close();
    }
}
