package LabaSecond;

import java.io.PrintWriter;
import java.util.Scanner;

public class kMax {

    kMax.Node root;

    public static class Node {
        int key;
        kMax.Node left;
        kMax.Node right;
        kMax.Node prev;
        int size;

        Node(int key, kMax.Node left, kMax.Node right, kMax.Node prev, int size) {
            this.left = left;
            this.right = right;
            this.prev = prev;
            this.key = key;
            this.size = size;
        }

        Node() {
            this(0, null, null, null, 1);
        }

    }

    private void makeLeftChildParent(kMax.Node temp, kMax.Node parent) {
        if ((temp == null) || (parent == null) || (parent.left != temp) || (temp.prev != parent)) {
            throw new RuntimeException("WRONG");
        }
        if (parent.prev != null) {
            if (parent == parent.prev.left) {
                parent.prev.left = temp;
            } else {
                parent.prev.right = temp;
            }
        }
        if (temp.right != null) {
            temp.right.prev = parent;
        }
        temp.prev = parent.prev;
        parent.prev = temp;
        parent.left = temp.right;
        temp.right = parent;
        parent.size = 0;
        temp.size = 0;
        if (parent.left != null) {
            parent.size = parent.left.size;
        }
        if (parent.right != null) {
            parent.size = parent.size + parent.right.size;
        }
        parent.size++;
        if (temp.left != null) {
            temp.size = temp.left.size;
        }
        if (temp.right != null) {
            temp.size = temp.size + temp.right.size;
        }
        temp.size++;
    }

    private void makeRightChildParent(kMax.Node temp, kMax.Node parent) {
        if ((temp == null) || (parent == null) || (parent.right != temp) || (temp.prev != parent)) {
            throw new RuntimeException("WRONG");
        }
        if (parent.prev != null) {
            if (parent == parent.prev.left) {
                parent.prev.left = temp;
            } else {
                parent.prev.right = temp;
            }
        }
        if (temp.left != null) {
            temp.left.prev = parent;
        }
        temp.prev = parent.prev;
        parent.prev = temp;
        parent.right = temp.left;
        temp.left = parent;
        parent.size = 0;
        temp.size = 0;
        if (parent.left != null) {
            parent.size = parent.left.size;
        }
        if (parent.right != null) {
            parent.size = parent.size + parent.right.size;
        }
        parent.size++;
        if (temp.left != null) {
            temp.size = temp.left.size;
        }
        if (temp.right != null) {
            temp.size = temp.size + temp.right.size;
        }
        temp.size++;
    }

    private void splay(kMax.Node x) {
        while (x.prev != null) {
            kMax.Node Parent = x.prev;
            kMax.Node GrandParent = Parent.prev;
            if (GrandParent == null) {
                if (x == Parent.left) {
                    makeLeftChildParent(x, Parent);
                } else {
                    makeRightChildParent(x, Parent);
                }
            } else {
                if (x == Parent.left) {
                    if (Parent == GrandParent.left) {
                        makeLeftChildParent(Parent, GrandParent);
                        makeLeftChildParent(x, Parent);
                    } else {
                        makeLeftChildParent(x, x.prev);
                        makeRightChildParent(x, x.prev);
                    }
                } else {
                    if (Parent == GrandParent.left) {
                        makeRightChildParent(x, x.prev);
                        makeLeftChildParent(x, x.prev);
                    } else {
                        makeRightChildParent(Parent, GrandParent);
                        makeRightChildParent(x, Parent);
                    }
                }
            }
        }
        root = x;
    }

    private kMax.Node findNode(int key) {
        kMax.Node PrevNode = null;
        kMax.Node z = root;
        while (z != null) {
            PrevNode = z;
            if (key > z.key) {
                z = z.right;
            } else if (key < z.key) {
                z = z.left;
            } else if(key == z.key) {
                splay(z);
                return z;
            }

        }
        if(PrevNode != null)
        {
            splay(PrevNode);
            return null;
        }
        return null;
    }

    void insert(int key) {
        kMax.Node z = root;
        kMax.Node x = null;
        while (z != null) {
            x = z;
            z.size++;
            if (key > x.key) {
                z = z.right;
            } else {
                z = z.left;
            }
        }
        z = new kMax.Node();
        z.key = key;
        z.prev = x;
        z.size = 1;
        if (x == null) {
            root = z;
        } else if (key > x.key) {
            x.right = z;
        } else {
            x.left = z;
        }
        splay(z);
    }

    void delete(kMax.Node node) {
        if (node == null) {
            return;
        }
        splay(node);
        if((node.left != null) && (node.right !=null)) {
            kMax.Node min = node.left;
            while(min.right != null) {
                min = min.right;
            }
            min.right = node.right;
            node.right.prev = min;
            node.left.prev = null;
            root = node.left;
        }
        else if (node.right != null) {
            node.right.prev = null;
            root = node.right;
        } else if( node.left !=null) {
            node.left.prev = null;
            root = node.left;
        }
        else {
            root = null;
        }
        node.prev = null;
        node.left = null;
        node.right = null;
        root.size++;
    }
    public void delete(int key) {
        delete(findNode(key));
    }

    public int find_k_max (int k) {
        Node x = root;
        k = root.size - k + 1;
        while (true) {
            if (x.left != null) {
                if (x.left.size + 1 == k) {
                    break;
                } else if (x.left.size == k) {
                    x = x.left;
                    while (x.right != null) {
                        x = x.right;
                    }
                    break;
                } else if (x.left.size > k) {
                    x = x.left;
                    continue;
                } else {
                    k = k - x.left.size - 1;
                    x = x.right;
                    continue;
                }
            }
            if (x.right != null) {
                if (k == 1) {
                    break;
                } else {
                    x = x.right;
                    k--;
                    continue;
                }
            }
            break;
        }
        return x.key;
    }

    public boolean exists (int key) {
        return findNode(key) != null;
    }

    public static void main (String args[]) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int c;
        int h;
        kMax tree = new kMax();
        for (int i = 0; i < n; i++) {
            c = in.nextInt();
            h = in.nextInt();
            if (c == 1) {
                if (!tree.exists(h)) {
                    tree.insert(h);
                }
            } else if (c == 0) {
                out.println(tree.find_k_max(h));
            } else {
                tree.delete(h);
            }
        }
        in.close();
        out.close();
    }

}
