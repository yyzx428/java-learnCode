package ProxyFramework.ProxyFunction;


public class GetInvocation {
    public static void main(String[] args) {
    }


    static class Student {
        public static int getStudent() {
            return ClassMate.getStudent();
        }
    }

    static class ClassMate {
        public static int getStudent() {
            return 1;
        }
    }
}
