package LabaSecond;

import java.util.Random;
import java.util.Scanner;

public class task_D {

    public static class Treap {
        Treap right;
        Treap left;
        int value;
        long priority;
        int size;
        int free;

        public Treap(int value) {
            this.priority = r.nextLong();
            size = 1;
            this.value = value;
            if(value == 0) {
                free = 1;
            } else {
                free = 1;
            }
        }
    }

    public static class Pair {
        Treap a;
        Treap b;

        public Pair(Treap a, Treap b) {
            this.a = a;
            this.b = b;
        }
    }

    public static int size(Treap t) {
        if(t == null) {
            return 0;
        }
        return t.size;
    }

    public static int free(Treap t) {
        if(t == null) {
            return 0;
        }
        return t.free;
    }

    public static void update(Treap t) {
        t.size = 1 + size(t.left) + size(t.right);
        if(free(t.left) == size(t.left)) {
            t.free = free(t.left) + 1 + free(t.right);
        } else {
            t.free = free(t.left);
        }
        if(t.value == 0) {
            t.free = free(t.left);
        }
    }


    public static Pair split(Treap t, int key) {
        if(t == null) {
            return new Pair(null, null);
        }
        if(key <= size(t.left)) {
            Pair p = split(t.left, key);
            t.left = p.b;
            update(t);
            return new Pair(p.a, t);
        }
        Pair p = split(t.right, key - size(t.left) - 1);
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

    public static Treap insert(Treap t, int l, int k) {
        l--;
        Pair p1 = split(t, l);
        Pair p2 = split(p1.b, 1);
        if(p2.a.value == 0) {
            p2.a.value = k;
            p2.a.free = free(p2.a.right) + 1;
            return merge(p1.a, merge(p2.a, p2.b));
        }
        Pair p3 = split(p2.b, free(p2.b));
        Pair p4 = split(p3.b, 1);
        Treap t1 = merge(p2.a, p3.a);
        Treap t2 = merge(t1, p4.b);
        Treap t3 = merge(new Treap(k), t2);
        return merge(p1.a, t3);
    }

    static int count = 0;
    static int s_count = 0;
    static int w = 0;
    static Random r;
    static StringBuilder sb = new StringBuilder();

    public static void dfs(Treap t, int mode) {
        if(t == null) {
            return;
        }
        dfs(t.left, mode);
        if(mode == 1 && count < w) {
            sb.append(t.value);
            sb.append(" ");
        }
        s_count++;
        if(t.value != 0) {
            count += s_count;
            s_count = 0;
        }
        dfs(t.right, mode);
    }

    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        r = new Random(1);
        Treap root = new Treap(0);
        for(int i = 1; i < m; i++) {
            root = merge(root, new Treap(0));
        }
        for(int i = 1; i < n + 1; i++) {
            root = insert(root, in.nextInt(), i);
        }
        dfs(root, 0);
        w = count;
        count = 0;
        s_count = 0;
        dfs(root, 1);
        System.out.println(w);
        System.out.println(sb.toString());
    }
}
