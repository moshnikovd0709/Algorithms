package Graph_2;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class taskF {

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

    public static long[] dikstra(int n, long[] d, ArrayList[] g) {
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
        return d;
    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        int a, b, c;
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
        a = in.nextInt();
        b = in.nextInt();
        c = in.nextInt();
        long[] da = new long[n];
        long[] db = new long[n];
        long[] dc = new long[n];
        for (int i = 0; i < n; i++) {
            db[i] = da[i] = dc[i] = Long.MAX_VALUE / 2;

        }
        da[a - 1] = 0;
        db[b - 1] = 0;
        dc[c - 1] = 0;
        dikstra(n, da, g);
        dikstra(n, db, g);
        dikstra(n, dc, g);
        long answer = Math.min(da[b - 1] + db[c - 1], da[c - 1] + dc[b - 1]);
        answer = Math.min(answer, dc[b - 1] + db[a - 1]);
        answer = Math.min(answer, db[c-1] + dc[a-1]);
        answer = Math.min(answer, db[a-1] + da[c-1]);
        answer = Math.min(answer, dc[a-1] + da[b-1]);
        if (answer > Long.MAX_VALUE / 2 - Integer.MAX_VALUE) {
            out.print(-1);
        } else {
            out.print(answer);
        }
        out.close();
        }
    }
