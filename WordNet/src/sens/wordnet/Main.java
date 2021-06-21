package sens.wordnet;

import sens.wordnet.assets.Bag;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
        Scanner In = new Scanner(System.in);
        System.out.print("Enter the no. of items: ");
        int n = In.nextInt();

        System.out.println("Enter the items: ");
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
        for (String s : bag) {
            System.out.println(s);
        }
        In.close();
    }
}