import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;

    Pair<K, V>[] table;

    int size;

    final private Pair<K, V> flag;

    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.size = 0;
        this.table = (Pair<K, V>[])new Pair[capacity];
        this.flag = new Pair<>(null, null);
    }

    public V search(K key) {
        int index = find(key);
        if(index == -1)
            return null;
        return table[index].second();
    }

    public void insert(K key, V value) {
        if (find(key) != -1) {
            int i = hashFunc.hash(key);
            while (table[i] != null)
                i = (i + 1) % capacity;
            table[i] = new Pair<>(key, value);
            size++;
        }
    }
    private int find(K key){
        int index = hashFunc.hash(key);
        for(int i = 0 ;i < capacity; i++){
            int curr = (i + index) % capacity;
            if (table[curr] == null)
                return -1;

            if (table[curr].first().equals(key))
                return curr;
        }
        return -1;
    }
    public boolean delete(K key) {
        int i = find(key);
        if (i == -1)
            return false;

        table[i] = flag;
        size--;
        return true;
    }


    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
}
