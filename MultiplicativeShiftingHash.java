import java.util.Random;

public class MultiplicativeShiftingHash implements HashFactory<Long> {
    final private Random rand;
    public MultiplicativeShiftingHash() {
        rand = new Random();
    }

    @Override
    public HashFunctor<Long> pickHash(int k) {
        int a = rand.nextInt(2, Integer.MAX_VALUE);
        return new Functor(a, k);
    }

    public class Functor implements HashFunctor<Long> {
        final public static long WORD_SIZE = 64;
        final private long a;
        final private long k;

        public Functor(long a, long k) {
            this.a = a;
            this.k = k;
        }
        @Override
        public int hash(Long key) {
            return (int)((a * key) >>> (WORD_SIZE - k));
        }

        public long a() {
            return a;
        }

        public long k() {
            return k;
        }
    }
}
