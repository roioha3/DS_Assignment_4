import java.util.Random;

public class StringHash implements HashFactory<String> {
    final private HashingUtils gen;
    final private Random rand;
    public StringHash() {
        gen = new HashingUtils();
        rand = new Random();
    }
    @Override
    public HashFunctor<String> pickHash(int k) {

        long q = gen.genLong(Integer.MAX_VALUE/2 + 1,(long)Integer.MAX_VALUE + 1);
        while(!gen.runMillerRabinTest(q, 50))
            q = gen.genLong(Integer.MAX_VALUE/2 + 1,(long)Integer.MAX_VALUE + 1);

        int c = rand.nextInt(2, (int)q - 1);

        return new Functor(c, (int)q, new ModularHash().pickHash(k));
    }

    public class Functor implements HashFunctor<String> {
        final private HashFunctor<Integer> carterWegmanHash;
        final private int c;
        final private int q;
        public Functor(int c, int q, HashFunctor<Integer> carterWegmanHash) {
            this.c = c;
            this.q = q;
            this.carterWegmanHash = carterWegmanHash;
        }
        @Override
        public int hash(String key) {
            int sum = 0;

            for(int i = 0; i < key.length(); i++){
               sum += HashingUtils.mod(key.charAt(i) * HashingUtils.modPow(c, key.length() - i, q), q);
            }
            return carterWegmanHash.hash(HashingUtils.mod(sum, q));
        }

        public int c() {
            return c;
        }

        public int q() {
            return q;
        }

        public HashFunctor carterWegmanHash() {
            return carterWegmanHash;
        }
    }
}
