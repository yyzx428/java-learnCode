package Algorithm.sort.quickSort;

public class QuickSort {
    public static void sort(int a[], int begin, int end) {
        if (begin >= end) return;
        int position = findPosition(a, begin, end);
        sort(a, begin, position - 1);
        sort(a, position + 1, end);
    }

    private static int findPosition(int[] a, int begin, int end) {
        int sentinel = a[end];
        int sentinel_index = end;

        for (int i = begin; i < end; i++) {
            if (a[i] <= sentinel) continue;
            sentinel_index--;
            int swap = a[sentinel_index];
            a[sentinel_index] = a[i];
            a[i] = swap;
        }

        int swap = a[sentinel_index];
        a[sentinel_index] = a[end];
        a[end] = swap;

        return sentinel_index;
    }
}
