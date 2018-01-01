
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/**
 * This is the assignment for the class Algorithms I, week 2 by Iori Oikawa.
 * A queue in which the item to be removed is chosen uniformly at random from
 * items in the queue.
 * 
 * Corner cases: The order of two or more iterators to the same randomized queue
 * must be mutually independent; each iterator must maintain its own random
 * order. Throw a java.lang.NullPointerException if the client attempts to add a
 * null item; throw a java.util.NoSuchElementException if the client attempts to
 * sample or dequeue an item from an empty randomized queue; throw a
 * java.lang.UnsupportedOperationException if the client calls the remove()
 * method in the iterator; throw a java.util.NoSuchElementException if the
 * client calls the next() method in the iterator and there are no more items to
 * return.
 * 
 * Performance requirements: Your randomized queue implementation must support
 * each randomized queue operation (besides creating an iterator) in constant
 * amortized time. That is, any sequence of m randomized queue operations
 * (starting from an empty queue) should take at most cm steps in the worst
 * case, for some constant c. A randomized queue containing n items must use at
 * most 48n + 192 bytes of memory. Additionally, your iterator implementation
 * must support operations next() and hasNext() in constant worst-case time; and
 * construction in linear time; you may (and will need to) use a linear amount
 * of extra memory per iterator.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    // Forming an elastic array...
    
    private Item[] a;
    private int size;
    
    // Constructing an empty randomized queue...

    public RandomizedQueue()
    {
        a = (Item[]) new Object[1];
        size = 0;
    }
    
    // Determination if the queue is empty...

    public boolean isEmpty()
    {
        return (size <= 0);
    }
   
    // Getting the number size of the queue...

    public int size()
    {
        return size;
    }
    
    // Adding an item to the queue...
    // Throw a java.lang.NullPointerException if the client attempts to add a null item.
    // Also doubles the length of the array when it is full.

    public void enqueue(Item item)
    {
        if (item == null) throw new java.lang.NullPointerException();
        if (size == a.length) resize(2 * a.length);
        a[size++] = item;
    }
    
    // Resizing the array to the [capacity] you want...
    // This is a quadratic operation in the length of a, and so should only be performed sparingly.
    // Amortizing this cost over the number of operations which can be performed in the new array.
    // However, the ResizingArray could be constant.

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) copy[i] = a[i];
        a = copy;
    }
    
    // Removing and return a random item...
    // Throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue.
    
    // This operation picks an element at random to return, then overwrites the Item at that index with the last Item in the array, then sets the last Item-containing element to null.
    // Also, halves the length of the array when it is one-quarter empty.

    public Item dequeue()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        
        int index = StdRandom.uniform(size);
        Item ans = a[index];
        
        if (index != size - 1) a[index] = a[size - 1];
        a[--size] = null;
        
        if (size >= 1 && size == a.length / 4) resize(a.length / 2);
        return ans;
    }
    
    // Returning (but do not remove) a random item...
    // Throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue.

    public Item sample()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        return a[StdRandom.uniform(size)];
    }
   
    @Override
    
    //Returning an independent iterator over items in random order...

    public Iterator<Item> iterator()
    {
       return new RandomizedQueueIterator();
    }
    
    //A copy of the original resizing array with the ability to dequeue only.

    private class RandomizedQueueIterator implements Iterator<Item> {
        
        // Storing items in the elastic array...
        
        private int subsize = size;
        private final Item[] copy;
        
        // Copying the items in the original array to the iterator.

        private RandomizedQueueIterator() {
            copy = (Item[]) new Object[subsize];
            for (int i = 0; i < subsize; i++) copy[i] = a[i];
        }
        
        @Override
        public boolean hasNext() { return subsize > 0; }
        
        @Override
        // Throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator.
        
        public void remove() { 
            throw new java.lang.UnsupportedOperationException();
        }
        
        @Override
        
        // Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
        // This method is dequeue from above, but without resizing.

        public Item next() {
            
            if (!hasNext()) throw new java.util.NoSuchElementException();
            
            int index = StdRandom.uniform(subsize);
            Item ans = copy[index];
            
            if (index != subsize - 1) copy[index] = copy[subsize - 1];
            copy[--subsize] = null;
            return ans;
        }
    }
    
    // Sending test unit...

    public static void main(String[] args)
    {
       
    }
}

