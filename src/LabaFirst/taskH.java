//package LabaFirst;
//
//import java.util.ArrayList;
//
//public class taskH {
//
//    public static class Pair {
//        int num;
//        int value;
//
//        public Pair(int num, int value) {
//            this.num = num;
//            this.value = value;
//        }
//    }
//
//    public static class Structure {
//        ArrayList<Pair> a;
//        ArrayList<Integer> indexes;
//
//        public Structure(int m) {
//            for (int i = 0; i < m; i++) {
//                indexes.add(-1);
//            }
//        }
//
//        public void swap(int x, int y) {
//            Pair t = a[x];
//            int indx = indexes[a[x].num];
//            indexes[a[x].num] = indexes[a[y].num];
//            indexes[a[y].num] = indx;
//            a[x] = a[y];
//            a[y] = t;
//        }
//
//        void siftUp(int v) {
//            while (a[v].value > a[(v - 1) / 2].value) {
//                swap(v, (v - 1) / 2);
//                v = (v - 1) / 2;
//            }
//        }
//
//        void siftDown(int v) {
//            while (2 * v + 1 < (int)a.size()) {
//                int l = 2 * v + 1;
//                int r = 2 * v + 2;
//                if (a[v].value > a[l].value) {
//                    if (r >= (int)a.size() || a[v].value > a[r].value) {
//                        break;
//                    }
//                }
//                int min = l;
//                if (r < (int)a.size() && a[l].value < a[r].value) {
//                    min = r;
//                }
//                swap(v, min);
//                v = min;
//            }
//        }
//
//        Pair extractMin() {
//            if (a.size() == 0) {
//                return Pair(-1, -1);
//            }
//            Pair min = a[0];
//            swap(0, (int)a.size() - 1);
//            a.erase(a.begin() + a.size() - 1);
//            siftDown(0);
//            return min;
//        }
//
//        void push(Pair p) {
//            a.push_back(p);
//            indexes[p.num] = (int)a.size() - 1;
//            siftUp((int)a.size() - 1);
//        }
//
//        void decreaseKey(int x, int v) {
//            int indx = indexes[x];
//            try {
//                a[indx].value = v;
//                siftUp(indx);
//            }
//            catch (exception) {
//                return;
//            }
//            extractMin();
//        }
//    }
//
//}
