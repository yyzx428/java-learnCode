package Algorithm.sort.heapSort;

public class HeapSort {
    public static void sort(int[] a, int length) {
        buildHeap(a, length);
        int k = length;
        while (k > 0) {
            swap(a, 0, k);
            heapify(a, k, 0);
            --k;
        }
    }

    private static void buildHeap(int[] a, int length) {
        for (int i = (length >>> 1) - 1; i >= 0; --i) {
            heapify(a, length, i);
        }
    }

    private static void heapify(int[] a, int length, int i) {
        int temp = a[i];//先取出当前元素i
        int half = length >>> 1;
        while(i < half){
            int left = (i << 1) + 1;
            int right = left + 1;
            int c = a[left];
            if(right < length && c < a[right]){
                c = a[left = right];
            }

            if(temp >= c){
                break;
            }

            a[i] = c;
            i = left;
        }

        a[i] = temp;
    }

    private static void swap(int[] a, int i, int maxPos) {
        a[i] ^= a[maxPos];
        a[maxPos] ^= a[i];
        a[i] ^= a[maxPos];
    }

}
