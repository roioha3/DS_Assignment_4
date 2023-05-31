import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;

    private int size;
    private Link<K, V>[] table;

    private class Link<T, U> {
        final private T key;
        final private U value;

        private Link<T, U> next;
        private Link<T, U> prev;
        public Link(T key, U value) {
            this.key = key;
            this.value = value;
        }
    }

    /*
     * You should add additional private members as needed.
     */

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);

        this.table = (Link<K, V>[])(new Link[capacity]);
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }

    }

    public V search(K key) {
        Link<K, V> l = find(key);
        if (l == null)
            return null;
        return l.value;
    }

    private Link<K, V> find(K key) {
        Link<K, V> curr = table[hashFunc.hash(key)];

        while (curr != null) {
            if (curr.key.equals(key)) {
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }
    public void insert(K key, V value) {
        if(search(key) == null) {
            int m = hashFunc.hash(key);
            Link<K, V> curr = table[m];
            Link<K, V> toInsert = new Link<K, V>(key, value);
            toInsert.next = curr;
            if(curr != null)
                curr.prev = toInsert;
            table[m] = toInsert;
            size++;

            if (size > maxLoadFactor * capacity)
                rehash();
        }
    }

    public void rehash() {
        Link<K, V>[] oldTable = table;
        capacity = capacity << 1;
        table = (Link<K, V>[])(new Link[capacity]);
        hashFunc = hashFactory.pickHash(capacity);
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }

        for (int i = 0; i < oldTable.length; i++) {
            while (oldTable[i] != null) {
                insert(oldTable[i].key, oldTable[i].value);
                oldTable[i] = oldTable[i].next;
            }
        }
    }
    public boolean delete(K key) {
        Link<K, V> toDelete = find(key);
        if(toDelete == null)
            return false;
        if(toDelete.prev != null)
            toDelete.prev.next = toDelete.next;
        if(toDelete.next != null )
            toDelete.next.prev = toDelete.prev;
        size--;
        return  true;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
}
