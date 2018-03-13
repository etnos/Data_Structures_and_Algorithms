package assignment1;
public class MaxPairwiseProductNaive {

    static MaxPairwiseReturn getMaxPairwiseProduct(long[] numbers) {
        long result = 0;
        int n = numbers.length;
        MaxPairwiseReturn ret = new MaxPairwiseReturn();
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (numbers[i] * numbers[j] > result) {
                    result = numbers[i] * numbers[j];
                    ret.max1 = numbers[i];
                    ret.max2 = numbers[j];
                    ret.result = result;
                }
            }
        }


        return ret;
    }

}
