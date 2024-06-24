public class MergeSort {
    public static void mergeSort(int[] array, int[] tempArray, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(array, tempArray, left, mid);
            mergeSort(array, tempArray, mid + 1, right);

            merge(array, tempArray, left, mid, right);
        }
    }

    private static void merge(int[] array, int[] tempArray, int left, int mid, int right) {
        System.arraycopy(array, left, tempArray, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (tempArray[i] <= tempArray[j]) {
                array[k] = tempArray[i];
                i++;
            } else {
                array[k] = tempArray[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            array[k] = tempArray[i];
            i++;
            k++;
        }
    }
}
