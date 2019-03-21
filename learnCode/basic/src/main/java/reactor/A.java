package reactor;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class A implements Serializable {
    public static void main(String[] args)  {
        A main = new A();
        Language language = new A().new Java();
        Language java = new A().new Java();

        main.sayHi(language);
        main.sayHi(java);
    }

    private Object readResolve() throws ObjectStreamException {
        return this;
    }

    private void sayHi(Java java) {
        System.out.println("Hi Java");
    }

    private void sayHi(Language language) {
        System.out.println("Im Language");
    }

    public class Java extends Language {}
    public abstract class Language {}
}