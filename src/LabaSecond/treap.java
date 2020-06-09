package LabaSecond;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class treap {
    static class Node {
        int key;
        int priority;
        int startIndex;
        Node left, right, parent;

        Node(int key, int priority, int index) {
            this.key = key;
            this.priority = priority;
            this.startIndex = index;
        }
    }

    static class Triple {
        int parent;
        int left;
        int right;

        Triple(int parent, int left, int right) {
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = Integer.parseInt(in.readLine());
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            String[] line = in.readLine().split(" +");
            int a = Integer.parseInt(line[0]);
            int b = Integer.parseInt(line[1]);
            nodes[i] = new Node(a, b, i);
        }
        Arrays.sort(nodes, Comparator.comparing(o -> o.key));
        Node last = nodes[0];
        Triple[] res = new Triple[n];
        for (int i = 0; i < res.length; i++) {
            res[i] = new Triple(-1, -1, -1);
        }
        for (int i = 1; i < n; i++) {
            Node add = nodes[i];
            Node prevRight = null;
            Node par = last;
            while (par != null && par.priority > add.priority) {
                prevRight = par;
                par = par.parent;
            }
            if (par == null) {
                res[add.startIndex].parent = -1;
            } else {
                par.right = add;
                add.parent = par;
                res[par.startIndex].right = add.startIndex;
                res[add.startIndex].parent = par.startIndex;
            }
            if (prevRight != null) {
                prevRight.parent = add;
                add.left = prevRight;
                res[prevRight.startIndex].parent = add.startIndex;
                res[add.startIndex].left = prevRight.startIndex;
            }
            last = add;
        }
        out.println("YES");
        for (int i = 0; i < res.length; i++) {
            out.println(res[i].parent + 1 + " " + (res[i].left + 1) + " " + (res[i].right + 1));
        }
        out.close();
    }
}
