package Algorithm.StackOperation;


import java.util.Arrays;

public class Stack<T> {
    private T[] values;
    private Integer top;
    private Integer size;

    @SuppressWarnings("unchecked")
    public Stack(Integer size) {
        this.size = size;
        this.top = -1;
        values = (T[]) new Object[size];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(T object) {
        if (top.equals(size - 1)) {
            T[] temp = Arrays.copyOf(values, size * 2);
            size = size * 2;
            values = temp;
        }
        values[++top] = object;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        return values[top--];
    }

    public T top() {
        if (isEmpty()) {
            return null;
        }
        return values[top];
    }
}
