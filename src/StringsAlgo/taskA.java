package StringsAlgo;

import java.io.*;
import java.util.Scanner;

public class taskA {

    public static int p = 97;
    public static long h = 123456789L;

    public static long gethash (long[] hash, int l, int r, long[]z) {
        long ans = (((hash[r] - Math.multiplyExact(hash[l - 1], z[r - l + 1])) % h) + h) % h;
        return ans;
    }

    public static long[] hash (String s, long[] hash, String letters[]) {
        hash[0] = s.charAt(0);
        for (int i = 1; i < letters.length; i++) {
            hash[i] = (hash[i-1]*p + letters[i].charAt(0)) % h;
        }
        return hash;
    }

    public static void main (String []args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int m = in.nextInt();
        long v = 1;
        long[] z = new long[s.length()];
        for (int i = 0; i < s.length(); i++) {
            z[i] = v;
            v = v*p;
            v = v%h;
        }
        String letters[] = s.split("");
        int a, b, c, d;
        PrintWriter out = new PrintWriter(System.out);
        long []ar = new long[s.length()];
        ar = hash(s, ar, letters);
        for (int i = 0; i < m; i++) {
            boolean flag = false;
            a = in.nextInt();
            b = in.nextInt();
            c = in.nextInt();
            d = in.nextInt();
            if (a == c) {
                if (d == b) {
                    flag = true;
                }
            } else if (a == 1) {
                if (ar[b - 1] == gethash(ar, c - 1, d - 1, z)) {
                    flag = true;
                }
            } else if (c == 1) {
                if (gethash(ar, a - 1, b - 1, z) == ar[d - 1]) {
                    flag = true;
                }
            } else {
                if (gethash(ar, a - 1, b - 1, z) == gethash(ar, c - 1, d - 1, z)) {
                    flag = true;
                }
            }
            if (flag) {
                out.println("Yes");
            } else {
                out.println("No");
            }
        }
        in.close();
        out.close();
    }
}
