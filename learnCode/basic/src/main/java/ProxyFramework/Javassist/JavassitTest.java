package ProxyFramework.Javassist;


import javassist.CannotCompileException;
import javassist.NotFoundException;
import ProxyFramework.Javassist.byteCode.ByteCodeFactory;
import ProxyFramework.Javassist.domain.People;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class JavassitTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, IOException, InstantiationException, CannotCompileException, NotFoundException, InvocationTargetException {
        ByteCodeFactory byteCodeFactory = new ByteCodeFactory();
        byteCodeFactory.generateCodeByte(People.class);
    }
}
