
public class TestFunction {
    public static void main(String[] args) {
        try {
            System.out.println(A.class.getDeclaredConstructor((Class[]) null).toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    static class A{

        int abs(){
            System.out.println(2);
            return 2;
        }
    }
}
