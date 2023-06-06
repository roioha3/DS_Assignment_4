import java.util.ArrayList;
import java.util.Collections; // can be useful
import java.util.Arrays;
import java.util.List;

public class HashingExperimentUtils {
    public static List<Integer> RandomOrderedValues(int start, int end, int step) {
        List<Integer> values = new ArrayList<>();
        for (int i = start; i <= end; i += step){
            values.add(i);
        }
        Collections.shuffle(values);
        return values;
    }
    
    final private static int k = 16;
    public static Pair<Double, Double> measureOperationsChained(double maxLoadFactor) {
        ChainedHashTable<Integer,Integer> hash = new ChainedHashTable<>(new ModularHash(),16,maxLoadFactor);
        double avgInsert = 0;
        double avgSearch = 0;
        int size = (int)((1 << 16) * maxLoadFactor - 1);
        List<Integer> values = RandomOrderedValues(0,size - 1,2);   
        for(Integer n: values){
            double startTime = System.nanoTime();
            hash.insert(n,n);
            avgInsert += System.nanoTime() - startTime;
        }


        avgInsert = avgInsert / values.size();
        values = RandomOrderedValues(0,size,1);
        for(Integer n: values) {
            double startTime = System.nanoTime();
            hash.search(n);
            avgSearch += System.nanoTime() - startTime;
        }
        avgSearch = avgSearch / values.size();
        return new Pair<>(avgInsert, avgSearch);
    }

    public static Pair<Double, Double> measureOperationsProbing(double maxLoadFactor) {
        ProbingHashTable<Integer, Integer> hashTable = new ProbingHashTable<>(new ModularHash(), 16, maxLoadFactor);
        int size = (int)((1 << 16) * maxLoadFactor - 1);
        List<Integer> toInsert = RandomOrderedValues(0, size - 1, 2);
        double sumInsert = 0;
        
        for (Integer i : toInsert) {
            double startTime = System.nanoTime();
            hashTable.insert(i, i);
            sumInsert += System.nanoTime() - startTime;
        }

        List<Integer> toSearch = RandomOrderedValues(0, size, 1);

        double sumSearch = 0;
        for (Integer i: toInsert) {
            double startTime = System.nanoTime();
            hashTable.search(i);
            sumSearch += System.nanoTime() - startTime;
        }
        return new Pair<>(sumInsert / toInsert.size(), sumSearch / toSearch.size());
    }

    private static void TestChaining(){
        double[] loadFactors =  {1.0/2, 3.0/4, 7.0/8, 15.0/16};
        for(double loadFactor: loadFactors) {
            double avgInsert = 0;
            double avgSearch = 0;
            for (int i = 0; i < 30; i++) {
                Pair<Double, Double> pair = measureOperationsChained(loadFactor);
                avgInsert += pair.first();
                avgSearch += pair.second();
            }
            avgInsert = avgInsert / 30;
            avgSearch = avgSearch / 30;
            System.out.println("\\hline");
            System.out.println("$"+ loadFactor +"$ & " + avgInsert + " & " + avgSearch + " \\\\");

        }
    }

    private static void TestProbing() {
        int times = 1;
        double[] loadFactors = {1/2.0, 3/4.0, 7/8.0, 15/16.0};

        for (double loadFac : loadFactors){
            double avgInsert = 0;
            double avgSearch = 0;
            System.out.println("\\hline");
            for (int i = 0; i < times; i++){
                Pair<Double, Double> avgs = measureOperationsProbing(loadFac);
                avgInsert += avgs.first();
                avgSearch += avgs.second();
            }
             System.out.println(loadFac + " & " + avgInsert / times + " & " + avgSearch / times + "\\\\");
        }
    }
    public static Pair<Double, Double> measureLongOperations() {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static Pair<Double, Double> measureStringOperations() {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static void main(String[] args) {
        TestChaining();
        System.out.println("---------------------");
        TestProbing();
    }
}
