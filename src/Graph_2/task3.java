package Graph_2;

import java.util.ArrayList;
import java.util.Scanner;

public class task3 {

    public static class Pair {
        int a;
        int b;
        long c;

        public Pair(int a, int b, long c) {
            this.c = c;
            this.b = b;
            this.a = a;
        }
    }

    public static void main (String []args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int a[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = in.nextInt();
            }
        }
        in.close();
        ArrayList<Pair> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((a[i][j] != 100000) && (a[i][j] != 0)) {
                    Pair p = new Pair(i, j, a[i][j]);
                    ans.add(p);
                }
            }
        }
        int x = -1;
        int []p = new int[n];
        long []d = new long[n];
        d[0] = 0;
        p[0] = -1;
        for (int i = 1; i < n; i++) {
            d[i] = Integer.MAX_VALUE / 2;
            p[i] = -1;
        }
        for (int i = 0; i < n; i++) {
            x = -1;
            for (int j = 0; j < ans.size(); j++) {
                if (d[ans.get(j).a] < Integer.MAX_VALUE/ 2) {
                    if (d[ans.get(j).b] > d[ans.get(j).a] + ans.get(j).c) {
                        d[ans.get(j).b] = Math.max(Integer.MIN_VALUE, d[ans.get(j).a] + ans.get(j).c);
                        p[ans.get(j).b] = ans.get(j).a;
                        x = ans.get(j).b;
                    }
                }
            }
        }
        if (x == -1) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
            int y = x;
            for (int i = 0; i < n; i++) {
                y = p[y];
            }
            ArrayList<Integer> path = new ArrayList<>();
            for (int cur=y; ; cur=p[cur]) {
                path.add(cur);
                if (cur == y && path.size() > 1) {
                    break;
                }
            }
            System.out.println(path.size() - 1);
            for (int i = path.size() - 2; i >= 0; i--) {
                System.out.print(path.get(i) + 1 + " ");

            }
        }
    }
}