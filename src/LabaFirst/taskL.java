package LabaFirst;

import java.util.Scanner;

public class taskL {

    public static void update(int x, int y, int z, int k, int t[][][]) {
        for (int i = x; i < t.length; i = (i | (i + 1))) {
            for (int j = y; j < t[0].length; j = (j | (j + 1))) {
                for (int p = z; p < t[0][0].length; p = (p | (p + 1))) {
                    t[i][j][p] += k;
                }
            }
        }
    }

    public static int request(int x1, int y1, int z1, int x2, int y2, int z2, int t[][][]) {
        return get(x2, y2, z2, t) - get(x2, y1 - 1, z2, t) - get(x2, y2, z1 - 1, t) + get(x1 - 1, y1 - 1, z2, t) - get(x1 - 1, y2, z2, t)
                + get(x2, y1 - 1, z1 - 1, t) - get(x1 - 1, y1 - 1, z1 - 1, t) +  get(x1 - 1, y2, z1 - 1, t);
    }

    public static int get(int x, int y, int z, int t[][][]) {
        int s = 0;
        for (int i = x; i >= 0; i = (i & (i + 1)) - 1) {
            for (int j = y; j >= 0; j = (j & (j + 1)) - 1) {
                for (int p = z; p >= 0; p = (p & (p + 1)) - 1) {
                    s = s + t[i][j][p];
                }
            }
        }
        return s;
    }

    public static void main (String args[]) {
        Scanner in = new Scanner(System.in);
        int operation;
        int n = in.nextInt();
        int t[][][] = new int[n][n][n];
        int x1;
        int x2;
        int y1;
        int y2;
        int z1;
        int z2;
        int k;
        while (true) {
            operation = in.nextInt();
            if (operation == 3) {
                break;
            } else if (operation == 1) {
                x1 = in.nextInt();
                x2 = in.nextInt();
                y1 = in.nextInt();
                k = in.nextInt();
                update(x1, x2, y1, k, t);
            } else {
                x1 = in.nextInt();
                y1 = in.nextInt();
                z1 = in.nextInt();
                x2 = in.nextInt();
                y2 = in.nextInt();
                z2 = in.nextInt();
                System.out.println(request(x1, y1, z1, x2, y2, z2, t));
            }
        }
        in.close();
    }
}
