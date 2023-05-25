import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SkipListExperimentUtils {
    public static double measureLevels(double p, int x) {
        IndexableSkipList skipList = new IndexableSkipList(p);
        double sumHeights = 0;
        for (int i = 0; i < x; i++){
            sumHeights += skipList.generateHeight();
        }
        return sumHeights / x;
    }

    /*
     * The experiment should be performed according to these steps:
     * 1. Create the empty Data-Structure.
     * 2. Generate a randomly ordered list (or array) of items to insert.
     *
     * 3. Save the start time of the experiment (notice that you should not
     *    include the previous steps in the time measurement of this experiment).
     * 4. Perform the insertions according to the list/array from item 2.
     * 5. Save the end time of the experiment.
     *
     * 6. Return the DS and the difference between the times from 3 and 5.
     */

    public static List<Integer> RandomOrderedValues(int start, int end, int step) {
        List<Integer> values = new ArrayList<>();
        for (int i = start; i <= end; i += step){
            values.add(i);
        }
        Collections.shuffle(values);
        return values;
    }
    public static Pair<AbstractSkipList, Double> measureInsertions(double p, int size) {
        List<Integer> values = RandomOrderedValues(0, 2 * size, 2);
        IndexableSkipList skipList = new IndexableSkipList(p);

        double totalTime = 0;
        for (int val : values) {
            double startTime = System.nanoTime();
            skipList.insert(val);
            totalTime += System.nanoTime() - startTime;

        }
        return new Pair<>(skipList, totalTime / values.size());
    }

    public static double measureSearch(AbstractSkipList skipList, int size) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static void Task2Dot2() {
        double[] probabilities = {0.33,0.5,0.75,0.9};
        for (int p = 0; p < probabilities.length; p++) {
            System.out.println("------------- p: " + probabilities[p] + " ---------------");
            int[] sequences = {10, 100, 1000, 10000};
            double expectency = 1 / probabilities[p];
            for (int x = 0; x < sequences.length; x++) {
                System.out.println("\\hline ");
                System.out.print(sequences[x] + " & ");
                double sumDiff = 0;
                for (int i = 0; i < 5; i++) {
                    double avg = measureLevels(probabilities[p], sequences[x]);
                    sumDiff += (avg - expectency);
                    System.out.print(avg + " & ");
                }
                System.out.println(expectency + " & " + sumDiff / 5 + " \\\\");
            }
        }
    }

    public static void Task2Dot6(){
        int numOfExp = 30;
        double[] probabilities = {0.33, 0.5, 0.75, 0.9};
        for (int p = 0; p < probabilities.length; p++) {
            System.out.println("------------------- " + probabilities[p] + " -----------------");
            int[] sequences = {1000, 2500, 5000, 10000, 15000, 20000, 50000};
            for (int x = 0; x < sequences.length; x++) {
                double sumInsertions = 0, sumSearches = 0, sumDeletions = 0;
                for (int i = 0; i < 30; i++) {
                    Pair<AbstractSkipList, Double> pair = measureInsertions(probabilities[p], sequences[x]);
                    AbstractSkipList skipList = pair.first();
                    sumInsertions += pair.second();
                    sumSearches += measureSearch(skipList, sequences[x]);
                    sumDeletions += measureDeletions(skipList, sequences[x]);
                }
                System.out.println("\\hline\n" +sequences[x] +
                        " & " + sumInsertions / numOfExp +
                        " & " + sumSearches / numOfExp +
                        " & " + sumDeletions / numOfExp + "\\\\ ");
            }
        }
    }
    public static void main(String[] args) {
        Task2Dot6();
    }
}
