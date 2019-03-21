package cyclerely;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Configurationer.class);
        Entity entity = context.getBean(Entity.class);
        System.out.println(entity.getName());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Documented
    @interface Custom {

    }

    @Configuration
    @Import({Entity.class, Domain.class, ASP.class})
    @EnableAspectJAutoProxy
    static class Configurationer {

    }

    @Aspect
    @Component
    static class ASP {

        @Around("@annotation(custom)")
        public Object round(ProceedingJoinPoint joinPoint, Custom custom) {
            Object result = null;
            try {
                System.out.println("进入切面");
                result = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                System.out.println(result);
                System.out.println("退出切面");
            }
            return result;
        }
    }

    @Service
    static class Entity {
        @Autowired
        Entity entity;

        @Autowired
        Domain domain;

        public String getName() {
            return entity.getCustomName();
        }

        @Custom
        public String getCustomName() {
            return domain.getName();
        }
    }

    @Service
    static class Domain {
        public String getName() {
            return "Domain Name";
        }
    }
}
