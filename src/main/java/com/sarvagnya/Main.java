package com.sarvagnya;

import java.util.ArrayList;

public class Main {

    public static Node generateTree(ArrayList<String> dataBlocks) {
        ArrayList<Node> childNodes = new ArrayList<>();

        for (String message : dataBlocks) {
            childNodes.add(new Node(null, null, HashAlgorithm.generateHash(message)));
        }

        return buildTree(childNodes);
    }

    private static Node buildTree(ArrayList<Node> children) {
        ArrayList<Node> parents = new ArrayList<>();

        while (children.size() != 1) {
            int index = 0, length = children.size();
            while (index < length) {
                Node leftChild = children.get(index);
                Node rightChild;

                if ((index + 1) < length) {
                    rightChild = children.get(index + 1);
                } else {
                    rightChild = new Node(null, null, leftChild.getHash());
                }

                String parentHash = HashAlgorithm.generateHash(leftChild.getHash() + rightChild.getHash());
                parents.add(new Node(leftChild, rightChild, parentHash));
                index += 2;
            }
            children = parents;
            parents = new ArrayList<>();
        }
        return children.get(0);
    }

    private static void display(Node node, int level) {
        if (node != null) {
            display(node.getRight(), level + 1);
            if (level != 0) {
                for (int i = 0; i < level - 1; ++i)
                    System.out.print("|\t");
                System.out.println("|----" + node.getHash().substring(0, 15));
            } else
                System.out.println(node.getHash().substring(0, 15));
            display(node.getLeft(), level + 1);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> dataBlocks = new ArrayList<>();

        // The grades for the 5 subjects are given in the transaction data
        dataBlocks.add("Tx(1, VJTI, Sarvagnya, AA AB BB BC CC)");
        dataBlocks.add("Tx(2, VJTI, Sarvagnya, AA AB AA AB BC)");
        dataBlocks.add("Tx(3, VJTI, Sarvagnya, CC AB AA BB AB)");
        dataBlocks.add("Tx(4, VJTI, Sarvagnya, BB AA AB BB BC)");
        dataBlocks.add("Tx(5, VJTI, Sarvagnya, BC CC AA AA AB)");
        dataBlocks.add("Tx(6, VJTI, Sarvagnya, AA BB CD AB BB)");
        dataBlocks.add("Tx(7, VJTI, Sarvagnya, AB CC AA BB BB)");
        dataBlocks.add("Tx(8, VJTI, Sarvagnya, AA BB AB AB CC)");
        dataBlocks.add("Tx(9, VJTI, Sarvagnya, BC BC AA AB AA)");
        dataBlocks.add("Tx(10, VJTI, Sarvagnya, AA BC BB AB CC)");

        // Printing the tree graphically
        Node root = generateTree(dataBlocks);
        display(root, 0);

        // Printing the Merkle root
        System.out.println("Merkle Root for the 10 transactions is: " + root.getHash());
    }
}