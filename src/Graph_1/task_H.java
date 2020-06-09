package Graph_1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class task_H {
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

    public static class Pair implements Comparable<Pair> {
        int a;
        int b;
        int c;

        Pair(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public int compareTo(Pair o) {
            if (c - o.c <= 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }

        public static int solve(Pair[] g, int n) {
            int[] t = new int[n];
            int ans = 0;
            for (int i = 0; i < n; i++) {
                t[i] = i;
            }
            for (Pair v : g) {
                int a = t[v.a];
                int b = t[v.b];
                if (a != b) {
                    ans += v.c;
                    for (int i = 0; i < n; i++) {
                        if (t[i] == b) {
                            t[i] = a;
                        }
                    }
                }
            }
            return ans;
        }

        public static void main(String[] args) {
            FastScanner in = new FastScanner(System.in);
            PrintWriter out = new PrintWriter(System.out);
            int n = in.nextInt();
            int m = in.nextInt();
            Pair[] g = new Pair[m];
            int a, b, c;
            for (int i = 0; i < m; i++) {
                a = in.nextInt() - 1;
                b = in.nextInt() - 1;
                c = in.nextInt();
                Pair x = new Pair(a, b, c);
                g[i] = x;
            }
            Arrays.sort(g);
            out.println(solve(g, n));
            out.close();
        }
    }
