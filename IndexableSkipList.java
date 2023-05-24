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
            while (curr.getNext(level).key() > val)
                level--;
            curr = curr.getNext(level);
        }
        if (level >= 0)
            curr = curr.getNext(level);
        return curr;
    }

    @Override
    public int generateHeight() {
       int height = 0;
       double p;
        do{
            height++;
            p = Math.random();
        } while(p < (1 - probability));
       return height;
    }

    public int rank(int val) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public int select(int index) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }
}
