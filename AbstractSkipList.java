import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractSkipList {
    final protected Node head;
    final protected Node tail;
    protected int size;

    public AbstractSkipList() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);
        size = 0;
        increaseHeight();
    }

    public void increaseHeight() {
        head.addLevel(tail, null);
        tail.addLevel(null, head);
        head.addPrevGap(0);
        tail.addPrevGap(size + 1);

    }

    abstract Node find(int key);

    abstract public int generateHeight();

    public Node search(int key) {
        Node curr = find(key);

        return curr.key() == key ? curr : null;
    }

    public Node insertWithHeight(int key, int nodeHeight) {
        System.out.println("----------------- " + key + " -----------------");
        Node prevNode = find(key);
        if (prevNode.key() == key) {
            return null;
        }

        size++;

        while (nodeHeight > head.height()) {
            increaseHeight();
        }

        Node newNode = new Node(key);
        newNode.addPrevGap(1);
        int sumGap = 1;
        for (int level = 0; level <= nodeHeight && prevNode != null; ++level) {
            Node nextNode = prevNode.getNext(level);
            //System.out.println(sumGap);
            newNode.addLevel(nextNode, prevNode);
            if (level < nodeHeight) {
                System.out.println("level " + level + " gap " + sumGap);
                newNode.addPrevGap(sumGap);
            }
            prevNode.setNext(level, newNode);
            nextNode.setPrev(level, newNode);
            nextNode.setPrevGap(level, nextNode.getPrevGap(level) - sumGap);
            //System.out.print(tail.getPrevGap(level) + ", ");
            sumGap = prevNode.getPrevGap(level) + 1;
            while (prevNode != null && prevNode.height() == level) {
                sumGap += prevNode.getPrevGap(level);
                prevNode = prevNode.getPrev(level);
            }
        }
        return newNode;
    }

    public Node insert(int key) {
        int nodeHeight = generateHeight();
        return insertWithHeight(key, nodeHeight);
    }

    public boolean delete(Node node) {
        for (int level = 0; level <= node.height(); ++level) {
            Node prev = node.getPrev(level);
            Node next = node.getNext(level);
            int newGap = next.getPrevGap(level) + node.getPrevGap(level) - 1;
            next.setPrevGap(level,newGap);
            prev.setNext(level, next);
            next.setPrev(level, prev);

        }
        size--;
        return true;
    }

    public int predecessor(Node node) {
        return node.getPrev(0).key();
    }

    public int successor(Node node) {
        return node.getNext(0).key();
    }

    public int minimum() {
        if (head.getNext(0) == tail) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return head.getNext(0).key();
    }

    public int maximum() {
        if (tail.getPrev(0) == head) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return tail.getPrev(0).key();
    }

    private void levelToString(StringBuilder s, int level) {
        s.append("H    ");
        Node curr = head.getNext(level);

        while (curr != tail) {
            s.append(curr.key);
            s.append("    ");
        }

        s.append("T\n");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int level = head.height(); level >= 0; --level) {
            levelToString(str, level);
        }

        return str.toString();
    }

    public static class Node {
        final private List<Node> next;
        final private List<Node> prev;
        private int height;
        final private int key;
        // Field for assignment 2.12.2
        private List<Integer> gapPrev;


        public Node(int key) {
            next = new ArrayList<>();
            prev = new ArrayList<>();
            gapPrev = new ArrayList<>();
            this.height = -1;
            this.key = key;
        }

        public Node getPrev(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return prev.get(level);
        }

        public Node getNext(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return next.get(level);
        }

        public int getPrevGap(int level) {
            if (level > height)
                throw new IllegalStateException("Fetching height higher than current node height");
            return gapPrev.get(level);
        }

        public void setNext(int level, Node next) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.next.set(level, next);
        }

        public void setPrev(int level, Node prev) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.prev.set(level, prev);
        }

        public void addLevel(Node next, Node prev) {
            ++height;

            this.next.add(next);
            this.prev.add(prev);
        }

        public void setPrevGap(int level, int prevGap){
            this.gapPrev.set(level, prevGap);
        }
        public void addPrevGap(int prevGap) {
            this.gapPrev.add(prevGap);
        }
        public int height() { return height; }
        public int key() { return key; }
    }
}
