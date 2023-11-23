package cc.abro.orchengine.services;

import cc.abro.orchengine.context.EngineService;

import java.util.*;

@EngineService
public class RandomService {

    private final Random random = new Random();

    public <T> T getRandomItemByWeight(Map<T, Number> itemByWeight) {
        List<T> items = new ArrayList<>(itemByWeight.keySet());
        double[] weights = items.stream()
                .map(itemByWeight::get)
                .mapToDouble(Number::doubleValue).toArray();
        return items.get(getRandomIndexByWeight(weights));
    }

    public int getRandomIndexByWeight(double... weights) {
        double[] prefixSum = getPrefixSum(weights);
        double weightsSum = prefixSum[prefixSum.length - 1];
        double randomValue = random.nextDouble(weightsSum);

        for (int i = 0; i < prefixSum.length; i++) {
            if (randomValue < prefixSum[i]) {
                return i;
            }
        }
        return prefixSum.length - 1;
    }

    public int getRandomIndexByWeight(int... weights) {
        double[] weightsDouble = Arrays.stream(weights)
                .mapToDouble(i -> (double) i)
                .toArray();
        return getRandomIndexByWeight(weightsDouble);
    }

    private double[] getPrefixSum(double[] array) {
        if (array.length == 0) {
            return new double[0];
        }

        double[] prefixSum = new double[array.length];
        prefixSum[0] = array[0];
        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + array[i];
        }
        return prefixSum;
    }

}
