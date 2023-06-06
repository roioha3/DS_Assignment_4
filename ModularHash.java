import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
    final private Random rand;
    public ModularHash() {
        rand = new Random();
    }

    @Override
    public HashFunctor<Integer> pickHash(int k) {
        int a = rand.nextInt(1, Integer.MAX_VALUE);
        int b = rand.nextInt(0, Integer.MAX_VALUE);
        HashingUtils gen = new HashingUtils();
        long p = gen.genLong((long)Integer.MAX_VALUE + 1, Long.MAX_VALUE);
        while(!gen.runMillerRabinTest(p,50))
            p = gen.genLong((long)Integer.MAX_VALUE + 1, Long.MAX_VALUE);
        int m = 1 << k;
        return new Functor(a,b,p,m);
    }

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private long p;
        final private int m;

        public Functor(int a, int b, long p, int m) {
            this.a = a;
            this.b = b;
            this.p = p;
            this.m = m;
        }


        @Override
        public int hash(Integer key) {
            return (int)HashingUtils.mod(HashingUtils.mod(((long)a * key + b), p),  m);
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
