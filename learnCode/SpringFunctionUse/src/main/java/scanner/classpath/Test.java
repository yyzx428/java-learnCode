package scanner.classpath;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("scanner/classpath/config/sample-spring-config.xml");
        System.out.println(context.getBean("template"));
    }
}
