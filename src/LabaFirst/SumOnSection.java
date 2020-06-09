package LabaFirst;

import java.util.Scanner;

public class SumOnSection {
    public static void main (String args[]) {
        long answer = 0;
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int x = in.nextInt();
        int y = in.nextInt();
        long[]a = new long[n];
        a[0] = in.nextInt();
        long prev = a[0];
        for (int i = 1; i < n; i++) {
            a[i] = (x * prev + y) & ((1 << 16) - 1);
            prev = a[i];
            a[i] = a[i] + a[i - 1];
        }
        int m = in.nextInt();
        int z = in.nextInt();
        int t = in.nextInt();
        int cur = in.nextInt();
        in.close();
        int first;
        int second;
        for (int i = 0; i < m; i++) {
                first = cur % n;
                cur = (z * cur + t) & ((1 << 30) - 1);
                second = cur % n;
                cur = (z * cur + t) & ((1 << 30) - 1);
            if (first == 0 || second == 0) {
                answer += a[Math.max(first,second)];
            } else {
                answer += a[Math.max(first, second)] - a[Math.min(first, second) - 1];
            }
        }
        System.out.println(answer);
    }
}
