package Algorithm.StackOperation;

public class StackTest {
    public static void main(String[] args) {
        // 基础的栈测试
        //baseStackTest();

    }

    private static void baseStackTest() {
        Stack<Integer> stack = new Stack<>(10000);
        int i = 0;
        for (; i < 10000; i++) {
            stack.push(i);
        }

        for (; i > 0; i--) {
            System.out.println(stack.pop());
        }
    }
}
