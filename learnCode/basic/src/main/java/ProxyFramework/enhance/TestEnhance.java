package ProxyFramework.enhance;

import ProxyFramework.enhance.BeanIdAware;
import org.springframework.asm.Type;
import org.springframework.cglib.core.ClassGenerator;
import org.springframework.cglib.core.Constants;
import org.springframework.cglib.core.DefaultGeneratorStrategy;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.*;
import org.springframework.cglib.transform.ClassEmitterTransformer;
import org.springframework.cglib.transform.TransformingClassGenerator;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestEnhance {

    private static final Callback[] CALLBACKS = new Callback[] {
            new BeanMethodInterceptor(),
            new BeanFactoryAwareMethodInterceptor(),
            NoOp.INSTANCE
    };

    private static final String BEAN_FACTORY_FIELD = "$$beanFactory";

    private static final ConditionalCallbackFilter CALLBACK_FILTER = new ConditionalCallbackFilter(CALLBACKS);

    public Class<?> enhance(Class<?> configClass, ClassLoader classLoader) {
        Class<?> enhancedClass = createClass(newEnhancer(configClass, classLoader));
        return enhancedClass;
    }

    private Enhancer newEnhancer(Class<?> superclass, ClassLoader classLoader) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superclass);
        enhancer.setInterfaces(new Class<?>[] {EnhancedConfiguration.class});
        enhancer.setUseFactory(false);
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        enhancer.setStrategy(new BeanFactoryAwareGeneratorStrategy(classLoader));
        enhancer.setCallbackFilter(CALLBACK_FILTER);
        enhancer.setCallbackTypes(CALLBACK_FILTER.getCallbackTypes());
        return enhancer;
    }

    private Class<?> createClass(Enhancer enhancer) {
        Class<?> subclass = enhancer.createClass();
        // Registering callbacks statically (as opposed to thread-local)
        // is critical for usage in an OSGi environment (SPR-5932)...
        Enhancer.registerStaticCallbacks(subclass, CALLBACKS);
        return subclass;
    }

    interface EnhancedConfiguration extends BeanIdAware {
    }


    private interface ConditionalCallback extends Callback {

        boolean isMatch(Method candidateMethod);
    }


    private static class BeanMethodInterceptor implements MethodInterceptor, ConditionalCallback {
        @Override
        public boolean isMatch(Method candidateMethod) {
            return false;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return null;
        }
    }

    private static class ConditionalCallbackFilter implements CallbackFilter {

        private final Callback[] callbacks;

        private final Class<?>[] callbackTypes;

        public ConditionalCallbackFilter(Callback[] callbacks) {
            this.callbacks = callbacks;
            this.callbackTypes = new Class<?>[callbacks.length];
            for (int i = 0; i < callbacks.length; i++) {
                this.callbackTypes[i] = callbacks[i].getClass();
            }
        }

        @Override
        public int accept(Method method) {
            for (int i = 0; i < this.callbacks.length; i++) {
                if (!(this.callbacks[i] instanceof ConditionalCallback) ||
                        ((ConditionalCallback) this.callbacks[i]).isMatch(method)) {
                    return i;
                }
            }
            throw new IllegalStateException("No callback available for method " + method.getName());
        }

        public Class<?>[] getCallbackTypes() {
            return this.callbackTypes;
        }
    }

    private static class BeanFactoryAwareGeneratorStrategy extends DefaultGeneratorStrategy {

        private final ClassLoader classLoader;

        public BeanFactoryAwareGeneratorStrategy(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        @Override
        protected ClassGenerator transform(ClassGenerator cg) throws Exception {
            ClassEmitterTransformer transformer = new ClassEmitterTransformer() {
                @Override
                public void end_class() {
                    declare_field(Constants.ACC_PUBLIC, BEAN_FACTORY_FIELD, Type.getType(String.class), null);
                    super.end_class();
                }
            };
            return new TransformingClassGenerator(cg, transformer);
        }

        @Override
        public byte[] generate(ClassGenerator cg) throws Exception {
            if (this.classLoader == null) {
                return super.generate(cg);
            }

            Thread currentThread = Thread.currentThread();
            ClassLoader threadContextClassLoader;
            try {
                threadContextClassLoader = currentThread.getContextClassLoader();
            }
            catch (Throwable ex) {
                // Cannot access thread context ClassLoader - falling back...
                return super.generate(cg);
            }

            boolean overrideClassLoader = !this.classLoader.equals(threadContextClassLoader);
            if (overrideClassLoader) {
                currentThread.setContextClassLoader(this.classLoader);
            }
            try {
                return super.generate(cg);
            }
            finally {
                if (overrideClassLoader) {
                    // Reset original thread context ClassLoader.
                    currentThread.setContextClassLoader(threadContextClassLoader);
                }
            }
        }
    }

    private static class BeanFactoryAwareMethodInterceptor implements MethodInterceptor, ConditionalCallback {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Field field = obj.getClass().getDeclaredField(BEAN_FACTORY_FIELD);
            Assert.state(field != null, "Unable to find generated BeanFactory field");
            field.set(obj,args[0]);

            // Does the actual (non-CGLIB) superclass actually implement BeanFactoryAware?
            // If so, call its setBeanFactory() method. If not, just exit.
            if (BeanIdAware.class.isAssignableFrom(obj.getClass().getSuperclass())) {
                return proxy.invokeSuper(obj, args);
            }
            return null;
        }

        @Override
        public boolean isMatch(Method candidateMethod) {
            return (candidateMethod.getName().equals("setBeanId") &&
                    candidateMethod.getParameterTypes().length == 1 &&
                    String.class == candidateMethod.getParameterTypes()[0] &&
                    BeanIdAware.class.isAssignableFrom(candidateMethod.getDeclaringClass()));
        }
    }

    public String getBeanId(Object enhancedConfigInstance) {
        Field field = ReflectionUtils.findField(enhancedConfigInstance.getClass(), BEAN_FACTORY_FIELD);
        Assert.state(field != null, "Unable to find generated bean factory field");
        Object beanFactory = ReflectionUtils.getField(field, enhancedConfigInstance);
        Assert.state(beanFactory != null, "BeanFactory has not been injected into @Configuration class");
        return (String) beanFactory;
    }


}
