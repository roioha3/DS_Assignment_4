import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
    public ModularHash() {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    @Override
    public HashFunctor<Integer> pickHash(int k) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public class Functor implements HashFunctor<Integer> {
        final private int a = 0;
        final private int b = 0;
        final private long p = 0;
        final private int m = 0;
        @Override
        public int hash(Integer key) {
            throw new UnsupportedOperationException("Replace this by your implementation");
        }

        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }
    }
}
