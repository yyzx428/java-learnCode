package scanner.fileSystem;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        FileSystemXmlApplicationContext fileContext =
                new FileSystemXmlApplicationContext("SpringFunctionUse/target/classes/scanner/classpath/config/sample-spring-config.xml");
        System.out.println(fileContext.getBean("template"));
    }
}
