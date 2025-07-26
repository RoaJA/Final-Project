package quickcall;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

    private class Node {
        Worker worker;
        Node left, right;

        Node(Worker worker) {
            this.worker = worker;
        }
    }

    private Node root;

    public void insert(Worker w, boolean sortByPrice) {
        root = insertRec(root, w, sortByPrice);
    }

    private Node insertRec(Node root, Worker w, boolean sortByPrice) {
        if (root == null) return new Node(w);

        if (sortByPrice) {
            if (w.getPrice() < root.worker.getPrice())
                root.left = insertRec(root.left, w, true);
            else
                root.right = insertRec(root.right, w, true);
        } else {
            if (w.getExperienceYears() < root.worker.getExperienceYears())
                root.left = insertRec(root.left, w, false);
            else
                root.right = insertRec(root.right, w, false);
        }
        return root;
    }

    public List<Worker> inOrder() {
        List<Worker> result = new ArrayList<>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(Node node, List<Worker> result) {
        if (node != null) {
            inOrderRec(node.left, result);
            result.add(node.worker);
            inOrderRec(node.right, result);
        }
    }
}

