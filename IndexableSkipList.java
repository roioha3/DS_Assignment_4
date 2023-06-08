public class IndexableSkipList extends AbstractSkipList {
    final protected double probability;
    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }

    @Override
    public Node find(int val) {
        Node curr = head;
        int level = head.height();
        while (level >= 0 && curr.getNext(level).key() != val){
            while (level >= 0 && curr.getNext(level).key() > val)
                level--;
            if (level >= 0)
                curr = curr.getNext(level);
        }
        if (level >= 0)
            curr = curr.getNext(level);
        return curr;
    }


    @Override
    public int generateHeight() {
       int height = 0;
       double p = Math.random();
       while (p < (1 - probability)){
           height++;
           p = Math.random();
        }
       return height;
    }

    public int rank(int val) {
        Node node = find(val);
        int rank = node.getPrevGap(node.height());
        Node prevNode = node.getPrev(node.height());
        for (int level = node.height(); level <= head.height(); level++) {
            while (prevNode != head & prevNode.height() == level){
                rank += prevNode.getPrevGap(level);
                prevNode = prevNode.getPrev(level);
            }
        }

        return rank;
    }

    public int select(int index) {
        int level = head.height();
        Node curr = head;
        int currRank = 0;
        for (; level >= 0 & currRank < index ; level--) {
            while (currRank + curr.getNext(level).getPrevGap(level) <= index){
                currRank += curr.getNext(level).getPrevGap(level);
                curr = curr.getNext(level);
            }
        }
        return curr.key();
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        Node curr = head;
        String s = "";
        while (curr != null){
            for (int i = 0; i <= curr.height(); i++) {
                s += curr.getPrevGap(i) + " ";
            }
            s += "\n";
            for (int i = 0; i <= curr.height(); i++) {
                s += curr.key() + " ";
            }
            s += "|\n";
            curr = curr.getNext(0);
        }
        return s;
    }
}
