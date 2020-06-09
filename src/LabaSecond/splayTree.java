package LabaSecond;

import java.util.Scanner;

public class splayTree {

    public static boolean flag_delete = false;
    Node root;

    public static class Node {
        int key;
        Node left;
        Node right;
        Node prev;

        Node(int key, Node left, Node right, Node prev) {
            this.left = left;
            this.right = right;
            this.prev = prev;
            this.key = key;
        }

        Node() {
            this(0, null, null, null);
        }

    }

    private void makeLeftChildParent(Node temp, Node parent) {
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
    }

    private void makeRightChildParent(Node temp, Node parent) {
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
    }

    private void splay(Node x) {
        while (x.prev != null) {
            Node Parent = x.prev;
            Node GrandParent = Parent.prev;
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

    private Node findNode(int key) {
        Node PrevNode = null;
        Node z = root;
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

    public boolean exists (int key) {
        return findNode(key) != null;
    }

    void insert(int key, boolean h) {
        Node z = root;
        Node x = null;
        while (z != null) {
            x = z;
            if (key > x.key) {
                z = z.right;
            } else {
                z = z.left;
            }
        }
        z = new Node();
        z.key = key;
        z.prev = x;
        if (x == null) {
            root = z;
        } else if (key > x.key) {
            x.right = z;
        } else {
            x.left = z;
        }
        splay(z);
    }
    public void insert(int key) {
        if (!exists(key)) {
            insert(key, true);
        }
    }

    void delete(Node node) {
        if (node == null) {
            return;
        }
        splay(node);
        if( (node.left != null) && (node.right !=null)) {
            Node min = node.left;
            while(min.right!=null) {
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
    }
    public void delete(int key) {
        delete(findNode(key));
    }

    int next(Node temp) {
        if (temp.right != null) {
            temp = temp.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            return temp.key;
        } else {
            while (temp.prev != null) {
                if (temp.prev.left != null && temp.prev.left == temp) {
                    temp = temp.prev;
                    return temp.key;
                }
                temp = temp.prev;
            }
        }
        return Integer.MIN_VALUE;
    }

    public int next (int key) {
        if (exists(key)) {
            return next(findNode(key));
        } else {
            insert(key);
            flag_delete = true;
            return next(findNode(key));
        }
    }

    int prev(Node temp) {
        if (temp.left != null) {
            temp = temp.left;
            while (temp.right != null) {
                temp = temp.right;
            }
            return temp.key;
        } else {
            while (temp.prev != null) {
                if (temp.prev.right != null && temp.prev.right == temp) {
                    temp = temp.prev;
                    return temp.key;
                }
                temp = temp.prev;
            }
        }
        return Integer.MIN_VALUE;
    }

    public int prev (int key) {
        if (exists(key)) {
            return prev(findNode(key));
        } else {
            insert(key);
            flag_delete = true;
            return prev(findNode(key));
        }
    }

    public static void main (String args[]) {
        Scanner in = new Scanner(System.in);
        String s;
        splayTree tree = new splayTree();
        int x;
        int y;
        while (in.hasNext()) {
            s = in.next();
            x = in.nextInt();
            if (s.charAt(0) == 'i') {
                tree.insert(x);
            } else if (s.charAt(0) == 'd') {
                tree.delete(x);
            } else if (s.charAt(0) == 'e') {
                System.out.println(tree.exists(x));
            } else if (s.charAt(0) == 'n') {
                y = tree.next(x);
                if (flag_delete) {
                    tree.delete(x);
                }
                flag_delete = false;
                if (y == Integer.MIN_VALUE) {
                    System.out.println("none");
                } else {
                    System.out.println(y);
                }
            } else if (s.charAt(0) == 'p') {
                y = tree.prev(x);
                if (flag_delete) {
                    tree.delete(x);
                }
                flag_delete = false;
                if (y == Integer.MIN_VALUE) {
                    System.out.println("none");
                } else {
                    System.out.println(y);
                }
            }
        }
        in.close();
    }
}
