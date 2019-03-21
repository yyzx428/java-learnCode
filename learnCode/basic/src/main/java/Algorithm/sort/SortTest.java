package Algorithm.sort;


import Algorithm.sort.mergeSort.MergeSort;
import Algorithm.sort.quickSort.QuickSort;

public class SortTest {
    public static void main(String[] args) {
        int[] a = {5, 4, 2, 10, 1, 16, 15, 12};
        //QuickSort.sort(a, 0, a.length - 1);
        MergeSort.sort(a, 0, a.length - 1);
        for (int value : a) {
            System.out.print(value + " ");
        }
    }
}
