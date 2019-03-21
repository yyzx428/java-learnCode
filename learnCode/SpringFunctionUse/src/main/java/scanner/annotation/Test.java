package scanner.annotation;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import scanner.annotation.domain.CycleEntity;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("scanner.annotation.config");
        context.refresh();
        System.out.println(context.getBean(CycleEntity.class));
    }
}
