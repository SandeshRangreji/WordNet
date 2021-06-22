package sens.wordnet.services;

import java.util.Iterator;
import java.util.Scanner;

public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;

    // initializes the variables
    public Bag() {
        first = null;
        n = 0;
    }

    // adds an element of type Item to the Bag
    public void add(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    // checks if bag is empty
    public boolean isEmpty() {
        return (first == null);
    }

    // returns size of bag
    public int size() {
        return n;
    }

    // makes an iterator which moves across the bag starting from the recently added item
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
            bag.add(item);// add() function is used
            n--;
        }

        if(bag.isEmpty()){
            // isEmpty() function is used
            System.out.println("The bag is empty");
            In.close();
            return;
        }

        System.out.println("Size of bag is: " + bag.size());// size() function is used

        System.out.println("Components of bag are:");
        for (String s : bag) {
            // iterator() function is used internally by utilized by the forEach function used here
            System.out.println(s);
        }
        In.close();
    }
}
