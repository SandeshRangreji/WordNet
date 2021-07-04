package sens.wordnet.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedIterator<Item> implements Iterator<Item> {
    private Node<Item> current;

    public LinkedIterator(Node<Item> first) {
        current = first;
    }

    public boolean hasNext() {
        return (current != null);
    }

    public Item next() {
        if (!hasNext())
            throw new NoSuchElementException();
        Item item = current.item;
        current = current.next;
        return item;
    }
}
