package Graph_1;

import java.io.*;
import java.util.*;

public class den {
        public static void main(String[] args) {
            String[] s_arr = new String[1];
            String s = "ABC";
            s_arr[0] = s;
            concatenate(s_arr, s);
            System.out.println(s + s_arr[0]);
        }
        static void concatenate(String[] s_arr, String s) {
            s = s + s_arr[0];
            s_arr[0] = s;
            s_arr = new String[1];
            s_arr[0] = "";
        }
    }
