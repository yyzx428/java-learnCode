package type.typeresolvable;

import lombok.Data;
import org.springframework.core.ResolvableType;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ResolvableType type = ResolvableType.forClass(ArrayList.class);
        ResolvableType genericsType = ResolvableType.forClassWithGenerics(Generics.class, Generics.class, Integer.class, String.class);
        Class<?> classese = type.resolve(Object.class);
        System.out.println(classese);
        System.out.println(List.class.isAssignableFrom(classese));
        System.out.println(genericsType.getGeneric(0,2));
    }

    @Data
    class Generics<T, F, C> {
        private T first;
        private F second;
        private C third;
    }
}
