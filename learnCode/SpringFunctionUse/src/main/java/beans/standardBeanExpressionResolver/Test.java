package beans.standardBeanExpressionResolver;


import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Test {
    public static void main(String[] args) {
        StandardBeanExpressionResolver resolver = new StandardBeanExpressionResolver();
        resolver.setExpressionPrefix("${");
        resolver.setExpressionSuffix("}");
        resolver.setExpressionParser(new SpelExpressionParser());
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerSingleton("user", new Student("张三"));
        Object value = resolver.evaluate("${ user.name }", new BeanExpressionContext(factory, null));
        System.out.println(value);
    }

    static class Student{
        private String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
