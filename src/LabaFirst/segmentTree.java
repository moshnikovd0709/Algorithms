package LabaFirst;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class segmentTree {

        public static boolean flag;

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
            long min;
            long add;
            long set;
            long value;

            public Pair(long min, long add, long set, long value) {
                this.min = min;
                this.add = add;
                this.set = set;
                this.value = value;
            }
        }

        public static void build(int a[], int v, int tl, int tr, Pair[] t) {
            if (tl == tr) {
                t[v].min = a[tl];
                t[v].set = Long.MAX_VALUE;
                t[v].add = 0;
                t[v].value = a[tl];
            } else {
                int tm = (tl + tr) / 2;
                build(a, v * 2, tl, tm, t);
                build(a, v * 2 + 1, tm + 1, tr, t);
                t[v].min = Math.min(t[v * 2].min, t[v * 2 + 1].min);
                t[v].set = Long.MAX_VALUE;
            }
        }

        public static void update(boolean flag,int v, int tl, int tr, int l, int r, long value, Pair[] t) {
            push(v, tl, tr, t);
            if (l == tl && tr == r) {
                if (flag) {
                    t[v].add += value;
                } else {
                    t[v].set = value;
                }
                push(v, tl, tr, t);
            } else if (l <= r) {
                int tm = (tl + tr) / 2;
                push(2*v, tl, tm, t);
                push(2*v + 1, tm + 1, tr, t);
                update(flag,v*2, tl, tm, l, Math.min(r, tm), value, t);
                update(flag, v*2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, value, t);
                t[v].min = Math.min(t[2*v].min, t[2*v + 1].min);
            }
        }

        public static long get(int v, int tl, int tr, int a, int b, Pair[] t) {
            push(v, tl, tr, t);
            if (a > b) {
                return Long.MAX_VALUE;
            }  //данечка+лерочка=love;
            if (tl == a) {
                if (tr == b) {
                    return t[v].min;
                }
            }
            int tm = (tl + tr) / 2;
            push(2*v, tl, tm, t);
            push(2*v + 1, tm + 1, tr, t);
            return Math.min(get(2*v, tl, tm, a, Math.min(b, tm), t), get(2*v + 1, tm + 1, tr, Math.max(a, tm + 1), b, t));
        }

        public static void push (int v, int tl, int tr, Pair[] t){
            if (t[v].set != Long.MAX_VALUE) {
                t[v].min = t[v].set + t[v].add;
                if (tl != tr) {
                    t[2*v].set = t[2*v + 1].set = t[v].set;
                    t[2*v].add = t[2*v + 1].add = t[v].add;
                }
                t[v].add = 0;
                t[v].set = Long.MAX_VALUE;
            } else {
                t[v].min += t[v].add;
                if (tl != tr) {
                    t[2*v].add += t[v].add;
                    t[2*v + 1].add += t[v].add;
                }
                t[v].add = 0;
            }
        }

        public static void main (String args[]){
                Scanner in = new Scanner(System.in);
                int n = in.nextInt();
                int a[] = new int[n];
                for (int i = 0; i < n; i++) {
                    a[i] = in.nextInt();
                }
                Pair[] tree = new Pair[4 * n];
                for (int i = 0; i < tree.length; i++) {
                    tree[i] = new Pair(0, 0,Long.MIN_VALUE, 0);
                }
                build(a, 1, 0, n - 1, tree);
                PrintWriter out = new PrintWriter(System.out);
                String s;
                long value;
                int l;
                int r;
                while (in.hasNext()) {
                    s = in.next();
                    if (s.charAt(0) == 's') {
                        l = in.nextInt() - 1;
                        r = in.nextInt() - 1;
                        value = in.nextLong();
                        flag = false;
                        update(flag,1, 0, n - 1, l, r, value, tree);
                    } else if (s.charAt(0) == 'a') {
                        l = in.nextInt() - 1;
                        r = in.nextInt() - 1;
                        value = in.nextLong();
                        flag = true;
                        update(flag, 1, 0, n - 1, l, r, value, tree);
                    } else {
                        l = in.nextInt() - 1;
                        r = in.nextInt() - 1;
                        System.out.println(get(1, 0, n - 1, l, r, tree));
                    }
                }
                in.close();
                out.close();
            }
    }
