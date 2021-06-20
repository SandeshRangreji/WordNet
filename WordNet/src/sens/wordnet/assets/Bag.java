package sens.wordnet.assets;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;

    public Bag() {
        first = null;
        n = 0;
    }

    public void add(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    public boolean isEmpty() {
        return (first == null);
    }

    public int size() {
        return n;
    }

    public Iterator<Item> iterator() {
        return new LinkedIterator<Item>(first);
    }
}
