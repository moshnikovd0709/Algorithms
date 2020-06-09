package LabaSecond;

import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class task_E {

    public static class Pair {
        Treap a;
        Treap b;

        public Pair(Treap a, Treap b) {
            this.a = a;
            this.b = b;
        }
    }

    public static class Treap {
        Treap right;
        Treap left;
        long priority;
        int size;
        long sum;
        int key;

        public Treap(int key) {
            this.priority = r.nextLong();
            size = 1;
            sum = key;
            this.key = key;
        }
    }

    public static void update(Treap t) {
        t.size = 1 + size(t.left) + size(t.right);
        t.sum = t.key + sum(t.left) + sum(t.right);
    }

    public static long sum(Treap t) {
        if(t == null) {
            return 0;
        }
        return t.sum;
    }

    public static int size(Treap t) {
        if(t == null) {
            return 0;
        }
        return t.size;
    }

    public static Pair split(Treap t, int key) {
        if(t == null) {
            return new Pair(null, null);
        }
        if(key <= t.key) {
            Pair p = split(t.left, key);
            t.left = p.b;
            update(t);
            return new Pair(p.a, t);
        }
        Pair p = split(t.right, key);
        t.right = p.a;
        update(t);
        return new Pair(t, p.b);
    }

    public static Treap merge(Treap t1, Treap t2) {
        if(t1 == null)
            return t2;
        if(t2 == null)
            return t1;
        if(t1.priority > t2.priority) {
            t2.left = merge(t1, t2.left);
            update(t2);
            return t2;
        }
        t1.right = merge(t1.right, t2);
        update(t1);
        return t1;
    }

    static Random r;

    public static Treap insert(Treap t, int key) {
        Pair p = split(t, key);
        p.b = (split(p.b, key + 1)).b;
        p.a = merge(p.a, new Treap(key));
        return merge(p.a, p.b);
    }

    public static long countSum(Treap t, int l, int r) {
        Pair p1 = split(t, l);
        Pair p2 = split(p1.b, r);
        long ans = sum(p2.a);
        merge(p2.a, p2.b);
        merge(p1.a, p1.b);
        return ans;
    }

    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        r = new Random();
        Treap root = null;
        boolean after = false;
        long y = 0;
        for(int i = 0; i < n; i++) {
            char a = in.next().charAt(0);
            int x = in.nextInt();
            if(a == '+') {
                if(!after) {
                    root = insert(root, x);
                } else {
                    root = insert(root, (int)(((long)x + y)%(1e9)));
                }
                after = false;
            }
            if(a == '?') {
                y = countSum(root, x, in.nextInt() + 1);
                out.println(y);
                after = true;
            }
        }
        out.close();
    }
}
