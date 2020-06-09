package LabaSecond;

import java.util.Scanner;

public class searchTree {

    public static boolean flag_delete = false;
    Node root;
    public static class Node {
        int key;
        Node left;
        Node right;
        Node prev;

        Node(Node prev, int key) {
            this.prev = prev;
            this.key = key;
        }

    }

    boolean exists(Node t, int key) {
        if (t == null) {
            return false;
        } else if (t.key == key) {
            return true;
        }
        if (key < t.key)
            return exists(t.left, key);
        else
            return exists(t.right, key);
    }
    public boolean exists (int key) {
        return exists (root, key);
    }

    Node insert(Node temp, Node prev, int key) {
        if (temp == null) {
            temp = new Node(prev, key);
        } else {
            if (key < temp.key)
                temp.left = insert(temp.left, temp, key);
            else
                temp.right = insert(temp.right, temp, key);
        }
        return temp;
    }
    public void insert(int key) {
        if (!exists(root, key)) {
            root = insert(root, null, key);
        }
    }

    void delete(Node temp, int key) {
        if (temp == null)
            return;
        if (key < temp.key)
            delete(temp.left, key);
        else if (key > temp.key)
            delete(temp.right, key);
        else if (temp.left != null && temp.right != null) {
            Node m = temp.right;
            while (m.left != null) {
                m = m.left;
            }
            temp.key = m.key;
            swap(m, m.right);
        } else if (temp.left != null) {
            swap(temp, temp.left);
        } else if (temp.right != null) {
            swap(temp, temp.right);
        } else {
            swap(temp, null);
        }
    }
    public void delete(int key) {
        delete(root, key);
    }
    void swap(Node a, Node b) {
        if (a.prev == null)
            root = b;
        else if (a == a.prev.left)
            a.prev.left = b;
        else
            a.prev.right = b;
        if (b != null)
            b.prev = a.prev;
    }

    int next(Node temp, int key) {
        while (true) {
            if (temp.key == key) {
                break;
            } else if (temp.key < key) {
                temp = temp.right;
            } else {
                temp = temp.left;
            }
        }
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
        if (exists(root, key)) {
            return next(root, key);
        } else {
            insert(key);
            flag_delete = true;
            return next(root, key);
        }
    }

    int prev(Node temp, int key) {
        while (true) {
            if (temp.key == key) {
                break;
            } else if (temp.key < key) {
                temp = temp.right;
            } else {
                temp = temp.left;
            }
        }
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
        if (exists(root, key)) {
            return prev(root, key);
        } else {
            insert(key);
            flag_delete = true;
            return prev(root, key);
        }
    }

    public static void main (String args[]) {
        Scanner in = new Scanner(System.in);
        String s;
        searchTree tree = new searchTree();
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
