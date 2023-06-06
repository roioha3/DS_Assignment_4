import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;


public class MyDataStructure {
    IndexableSkipList skipList;
    HashTable<Integer, AbstractSkipList.Node> hashTable;

    /*
     * You may add any members that you wish to add.
     * Remember that all the data-structures you use must be YOUR implementations,
     * except for the List and its implementation for the operation Range(low, high).
     */

    /***
     * This function is the Init function described in Part 4.
     *
     * @param N The maximal number of items expected in the DS.
     */
    public MyDataStructure(int N) {
        skipList = new IndexableSkipList(0.5);
        hashTable = new ProbingHashTable<>(new ModularHash());
    }

    /*
     * In the following functions,
     * you should REMOVE the place-holder return statements.
     */
    public boolean insert(int value) {
        AbstractSkipList.Node nodeSkipList = skipList.insert(value);
        if(nodeSkipList == null)
            return false;
        hashTable.insert(value,nodeSkipList);
        return true;
    }

    public boolean delete(int value) {
        AbstractSkipList.Node nodeToDelete = hashTable.search(value);
        if(nodeToDelete == null)
            return false;
        skipList.delete(nodeToDelete);
        hashTable.delete(value);
        return true;
    }

    public boolean contains(int value) {
        return hashTable.search(value) != null;
    }

    public int rank(int value) {
        return skipList.rank(value);
    }

    public int select(int index) {
        return skipList.select(index);
    }

    public List<Integer> range(int low, int high) {
        List<Integer> output = new LinkedList<>();
        AbstractSkipList.Node lowNode = hashTable.search(low);
        if(lowNode == null)
            return null;
        while(lowNode.key() <= high && lowNode.key() != Integer.MAX_VALUE){
            output.add(lowNode.key());
            lowNode.getNext(0);
        }
        return output;
    }
}
