package Algorithm.search.BinarySearch;

public class BinarySearch {
    public static int normal(int[] a, int target) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] == target) return mid;
            if (a[mid] >= target) high = mid - 1;
            else low = mid + 1;
        }
        return -1;
    }

    public static int findFirstAppear(int[] a, int target) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] == target) {
                if (mid == 0 || a[mid - 1] != a[mid])
                    return mid;
                else
                    high = mid - 1;
            }
            if (a[mid] >= target) high = mid - 1;
            else low = mid + 1;
        }
        return -1;
    }

    public static int findLastAppear(int[] a, int target) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] == target) {
                if (mid == a.length - 1 || a[mid + 1] != a[mid])
                    return mid;
                else
                    low = mid + 1;
            }
            if (a[mid] > target) high = mid - 1;
            else low = mid + 1;
        }
        return -1;
    }

    public static int findFirstBigger(int[] a, int target) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] >= target) {
                if (mid == 0 || a[mid - 1] < target) {
                    return mid;
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public static int findFirstMin(int[] a,int target){
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > target) {
                high = mid - 1;
            } else {
                if(mid == a.length-1 || a[mid+1] > target){
                    return mid;
                }
                low = mid + 1;
            }
        }
        return -1;
    }
}
