package LabaFirst;

import java.io.*;
import java.util.Scanner;

public class taskD {

    public static int[] b;
    public static Pair[] a;

    public static class Pair {
        int sum;
        int var;
        boolean end1;
        boolean end2;

        public Pair(int sum, int var, boolean end1, boolean end2) {
            this.sum = sum;
            this.var = var;
            this.end1 = end1;
            this.end2 = end2;
        }
    }

    public static void push(int v, int l, int r) {
        if (b[v] == 0) {
            return;
        }
        a[v].sum = (r - l + 1) * (b[v] - 1);
        a[v].end2 = a[v].end1 = false;
        a[v].var = b[v] - 1;
        if (r > l) {
            b[v * 2 + 1] = b[v * 2 + 2] = b[v];
        }
        b[v] = 0;
    }

    public static void update(int v, int l, int r, int x, int y, int z) {
        push(v, l, r);
        if (x > y) {
            return;
        }
        if (l == x && r == y) {
            b[v] = z + 1;
            push(v, l, r);
        } else {
            int m = (l + r) >> 1;
            update(v + v + 1, l, m, x, Math.min(y, m), z);
            update(v + v + 2, m + 1, r, Math.max(x, m + 1), y, z);
            push(v + v + 1, l, m);
            push(v + v + 2, m + 1, r);
            a[v] = new Pair(a[v * 2 + 1].sum + a[v * 2 + 2].sum, a[v * 2 + 1].var + a[v * 2 + 2].var, a[v * 2 + 1].end1, a[v * 2 + 2].end2);
            if (a[v + v + 1].end2 && a[v + v + 2].end1) {
                a[v].var--;
            }
        }
    }

    public static void main (String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int size = 10000007;
        b = new int[4 * size];
        a = new Pair[4 * size];
        for (int i = 0; i < a.length; i++) {
            a[i] = new Pair(0,0,false,false);
        }
        PrintWriter out = new PrintWriter(System.out);
        String s;
        int first;
        int second;
        for (int i = 0; i < n; i++) {
            s = in.next();
            first = in.nextInt();
            second = in.nextInt();
            first += 500001;
            if (s.charAt(0) == 'W') {
                update(0, 0, size - 1, first, second + first - 1, 0);
                System.out.println(a[0].var + " " + a[0].sum);
            } else {
                update(0, 0, size - 1, first, second + first - 1, 1);
                System.out.println(a[0].var + " " + a[0].sum);
            }
        }
            in.close();
            out.close();
        }
    }
