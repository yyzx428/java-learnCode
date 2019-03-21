package Algorithm.sort.mergeSort;

public class MergeSort {
    public static void sort(int[] a, int begin, int end) {
        if (begin >= end) return;
        int mid = begin + ((end - begin) >> 1);
        sort(a, begin, mid);
        sort(a, mid + 1, end);
        merge(a, begin, mid, end);
    }

    private static void merge(int[] a, int begin, int mid, int end) {
        int begin_length = mid - begin + 1;
        int end_length = end - mid;

        int[] begin_array = new int[begin_length];
        int[] end_array = new int[end_length];

       /* System.arraycopy(a, begin, begin_array, 0, begin_length);
        System.arraycopy(a, mid + 1, end_array, 0, end_length);*/

        for (int i = 0; i < begin_length; i++) {
            begin_array[i] = a[begin + i];
        }

        for (int j = 0; j < end_length; j++) {
            end_array[j] = a[mid + 1 + j];
        }

        int i = 0;
        int j = 0;

        int k = begin;
        while (i < begin_length && j < end_length) {
            if (begin_array[i] <= end_array[j]) {
                a[k] = begin_array[i++];
            } else {
                a[k] = end_array[j++];
            }
            k++;
        }

        while (i < begin_length) {
            a[k++] = begin_array[i++];
        }

        while (j < end_length) {
            a[k++] = end_array[j++];
        }
    }


}
