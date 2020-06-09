package LabaThird;

import java.io.*;
import java.util.StringTokenizer;

public class linkCutTree {

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

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        Node[] a = new Node[n + 1];
        for (int i = 0; i <= n; i++) {
            a[i] = new Node(null,null,null,false);
        }
        int m = in.nextInt();
        String s;
        int u, v;
        boolean answer;
        for (int i = 0; i < m; i++) {
            s = in.next();
            u = in.nextInt();
            v = in.nextInt();
            if (s.charAt(1) == 'u') {
                cut(a[u], a[v]);
            } else if (s.charAt(1) == 'i') {
                link(a[u], a[v]);
            } else {
                answer = connected(a[u], a[v]);
                if (answer) {
                    out.println(1);
                } else {
                    out.println(0);
                }
            }
        }
        out.close();
    }

    public static void link(Node v, Node u) {
        makeRoot(v);
        v.parent = u;
    }

    public static void cut(Node v, Node u) {
        makeRoot(v);
        expose(u);
        u.right.parent = null;
        u.right = null;
    }

    static void connect(Node ch, Node p, Boolean isLeftChild) {
        if (ch != null)
            ch.parent = p;
        if (isLeftChild != null) {
            if (isLeftChild)
                p.left = ch;
            else
                p.right = ch;
        }
    }

    static void makeLeftChildParent(Node temp, Node parent) {
        if ((temp == null) || (parent == null) || (parent.left != temp) || (temp.parent != parent)) {
            throw new RuntimeException("WRONG");
        }
        if (parent.parent != null) {
            if (parent == parent.parent.left) {
                parent.parent.left = temp;
            } else {
                parent.parent.right = temp;
            }
        }
        if (temp.right != null) {
            temp.right.parent = parent;
        }
        temp.parent = parent.parent;
        parent.parent = temp;
        parent.left = temp.right;
        temp.right = parent;
    }

    static void makeRightChildParent(Node temp, Node parent) {
        if ((temp == null) || (parent == null) || (parent.right != temp) || (temp.parent != parent)) {
            throw new RuntimeException("WRONG");
        }
        if (parent.parent != null) {
            if (parent == parent.parent.left) {
                parent.parent.left = temp;
            } else {
                parent.parent.right = temp;
            }
        }
        if (temp.left != null) {
            temp.left.parent = parent;
        }
        temp.parent = parent.parent;
        parent.parent = temp;
        parent.right = temp.left;
        temp.left = parent;
    }
    static void rotate(Node x) {
        Node p = x.parent;
        Node g = p.parent;
        boolean isRootP = p.isRoot();
        boolean leftChildX = (x == p.left);
        connect(leftChildX ? x.right : x.left, p, leftChildX);
        connect(p, x, !leftChildX);
        connect(x, g, !isRootP ? p == g.left : null);
    }

    static void splay(Node v) {
        while (!v.isRoot()) {
            Node p = v.parent;
            Node g = p.parent;
            if (!p.isRoot())
                g.push();
            p.push();
            v.push();
            if (!p.isRoot())
                rotate((v == p.left) == (p == g.left) ? p : v);
            rotate(v);
        }
        v.push();
    }

    static Node expose(Node x) {
        Node last = null;
        for (Node y = x; y != null; y = y.parent) {
            splay(y);
            y.left = last;
            last = y;
        }
        splay(x);
        return last;
    }

    public static void makeRoot(Node x) {
        expose(x);
        x.revert = !x.revert;
    }

    public static boolean connected(Node x, Node y) {
        if (x == y) {
            return true;
        }
        expose(x);
        expose(y);
        return x.parent != null;
    }

    public static class Node {
        Node left;
        Node right;
        Node parent;
        boolean revert;

        public Node(Node left, Node right, Node parent, boolean revert) {
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.revert = revert;
        }
        boolean isRoot() {
            return parent == null || (parent.left != this && parent.right != this);
        }

        void push() {
            if (revert) {
                revert = false;
                Node t = left;
                left = right;
                right = t;
                if (left != null) {
                    left.revert = !left.revert;
                }
                if (right != null) {
                    right.revert = !right.revert;
                }
                }
            }
        }
    }
