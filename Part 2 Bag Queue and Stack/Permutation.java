/******************************************************************************
 *  Compilation:  javac Permutations.java
 *  Execution:    java Permutations n
 *  
 *  Enumerates all permutations on n elements.
 *  Two different approaches are included.
 *
 *  % java Permutations 3
 *  abc
 *  acb
 *  bac 
 *  bca
 *  cab
 *  cba
 *  Copyright © 2000–2017, Robert Sedgewick and Kevin Wayne. 
 * Last updated: Fri Oct 20 14:12:12 EDT 2017.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> strings = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            strings.enqueue(StdIn.readString());
        }
        while (n != 0) {
            StdOut.println(strings.dequeue());
            n--;
        }
    }
}
