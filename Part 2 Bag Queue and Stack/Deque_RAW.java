
import java.util.Iterator;

/*
 * This is the assignment for the class Algorithms I, week 2 by Iori Oikawa.
 * A generalization of a stack and a queue that supports adding and removing
 * items from either the front or the back of the data structure.
 * 
 * Corner cases: Throw a java.lang.NullPointerException if the client attempts
 * to add a null item; throw a java.util.NoSuchElementException if the client
 * attempts to remove an item from an empty deque; throw a
 * java.lang.UnsupportedOperationException if the client calls the remove()
 * method in the iterator; throw a java.util.NoSuchElementException if the
 * client calls the next() method in the iterator and there are no more items
 * to return.
 * 
 * Performance requirements:   Your deque implementation must support each
 * deque operation in constant worst-case time. A deque containing n items must
 * use at most 48n + 192 bytes of memory. and use space proportional to the
 * number of items currently in the deque. Additionally, your iterator
 * implementation must support each operation (including construction) in
 * constant worst-case time.
 * 
 */

public class Deque<Item> implements Iterable<Item> {
    
    // Form a doubly-linked list for storing...
    
    private Node<Item> first;
    private Node<Item> last;
    private int size = 0;
    
    // Construct an empty deque...
    
    public Deque()
    {
        first = null;
        last = null;
    }
    
    // Determination for empty deuqe...
    
    public boolean isEmpty()
    {
        return (size <= 0);
    }
    
    // Return the size of the deque...
    
    public int size()
    {
        return size;
    }
    
    // Adding the item to the front of the list...
    // Throw a java.lang.NullPointerException if the client attempts to add a null item.
    
    public void addFirst(Item item)
    {
        if (item == null) throw new java.lang.NullPointerException(); // If there is no item...
        
        Node<Item> oldfirst = first;
        first = new Node<>(item);
        
        if (isEmpty()) last = first;
        else {
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        size++;
    }
    
    // Add the item to the end...
    // Throw a java.lang.NullPointerException if the client attempts to add a null item.
    
    public void addLast(Item item)
    {
        if (item == null) throw new java.lang.NullPointerException(); // If there is null item...
        
        Node<Item> oldlast = last;
        last = new Node<>(item);
        
        if (isEmpty()) first = last;
        else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        size++;
    }
    
    // Remove and return the item from the front of the list...
    // Throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque.

    public Item removeFirst()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException(); // If the deque is already empty...
        
        Item item = first.item;
        
        if (first.next != null) first = first.next;
        first.previous = null;
        size--;
        
        if (isEmpty()) {
            last = null;
            first = null;
        }
        return item;
    }
    
    // Removing and return the item from the end...
    // Throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque.

    public Item removeLast()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        
        Item item = last.item;
        
        if (last.previous != null) last = last.previous;
        last.next = null;
        size--;
        
        if (isEmpty()) {
            first = null;
            last = null;
        }
        return item;
    }
    
    // Return an iterator over items in order from front to end..."
    // @return 
    
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;
        
        @Override
        public boolean hasNext() { return current != null; }
        
        @Override
        // Throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator.

        public void remove() { 
            throw new java.lang.UnsupportedOperationException();
        }
        
        // Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    // The Node is the class from which a linked list is built.
    // The deque here relies on a doubly-linked list implementation.

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
        
        private Node(Item item) {
            this.item = item;
            next = null;
            previous = null;
        }
    }
    
    // Unit testing import...

    public static void main(String[] args)
    {
        
    }
}
