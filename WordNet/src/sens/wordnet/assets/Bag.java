package sens.wordnet.assets;

import java.util.Iterator;
import java.util.Scanner;

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

    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
        Scanner In = new Scanner(System.in);
        System.out.print("Enter the no. of words: ");
        int n = In.nextInt();

        System.out.println("Enter the words: ");
        while (n > 0) {
            String item = In.next();
            bag.add(item);
            n--;
        }

        if(bag.isEmpty()){
            System.out.println("The bag is empty");
            In.close();
            return;
        }

        System.out.println("Size of bag is: " + bag.size());
        System.out.println("Components of bag are:");
        for (String s : bag) {
            System.out.println(s);
        }
        In.close();
    }
}
