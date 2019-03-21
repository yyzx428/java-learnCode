package ProxyFramework.enhance;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class EnhanceTest {
    public static void main(String[] args) {
        TestEnhance testEnhance=new TestEnhance();
        Class a=testEnhance.enhance(Student.class,Thread.currentThread().getContextClassLoader());
        for(Field e:a.getDeclaredFields()){
            System.out.println(e.getName()+" "+e.getType()+" "+e.getDeclaringClass());
        }

        try {
            TestEnhance.EnhancedConfiguration object= (TestEnhance.EnhancedConfiguration)ReflectionUtils.accessibleConstructor(a).newInstance();
            object.setBeanId("1");
            System.out.println(testEnhance.getBeanId(object));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
