package Algorithm.search.BinarySearch;

public class Test {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] b = {1, 1, 2, 2, 2, 3, 3, 4, 4, 4};
        int[] c = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        int index = BinarySearch.findFirstMin(c, 14);
        if (index == -1) {
            System.out.println("值不存在");
        } else {
            System.out.println(index + " : " + c[index]);
        }
    }
}
