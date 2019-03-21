package beans.reflection;

import java.lang.reflect.Field;

public class Test {

    public static void main(String[] args) {
        for(Field field :Sample.class.getDeclaredFields()){
            System.out.println(field.getName());
        }
    }

    static class Sample{
        private String name;
        public static String classmeta;
    }
}
