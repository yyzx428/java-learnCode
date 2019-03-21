package ProxyFramework.Javassist.byteCode;


import javassist.*;
import javassist.bytecode.AccessFlag;
import ProxyFramework.Javassist.domain.Operation;
import ProxyFramework.Javassist.domain.People;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ByteCodeFactory {
    public Object generateCodeByte(Class<?> origin) throws NotFoundException, CannotCompileException,
            IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassPool pool = ClassPool.getDefault();
        CtClass proxyCc = pool.makeClass(origin.getName() + "$ProxyClass");
        CtClass operationCc = pool.get(origin.getName());

        CtField operationField = new CtField(operationCc, "origin", proxyCc);
        operationField.setModifiers(AccessFlag.PRIVATE);
        proxyCc.addField(operationField);

        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{operationCc}, proxyCc);
        ctConstructor.setBody("$0.origin = $1;");
        proxyCc.addConstructor(ctConstructor);

        CtClass interfaceCc = pool.get(Operation.class.getName());
        proxyCc.addInterface(interfaceCc);

        CtMethod[] ctMethods = interfaceCc.getDeclaredMethods();
        for (int i = 0; i < ctMethods.length; i++) {
            String methodBody = "$0.origin.getName()";

            // 如果方法有返回类型，则需要转换为相应类型后返回
            if (CtPrimitiveType.voidType != ctMethods[i].getReturnType()) {
                // 对8个基本类型进行转型
                // 例如：((Integer)this.origin.getName()).intValue();
                if (ctMethods[i].getReturnType() instanceof CtPrimitiveType) {
                    CtPrimitiveType ctPrimitiveType = (CtPrimitiveType) ctMethods[i].getReturnType();
                    methodBody = "return ((" + ctPrimitiveType.getWrapperName() + ") " + methodBody + ")." + ctPrimitiveType.getGetMethodName() + "()";
                }
                // 对于非基本类型直接转型即可
                else {
                    methodBody = "return (" + ctMethods[i].getReturnType().getName() + ") " + methodBody;
                }
            }
            methodBody += ";";

            /* 为代理类添加方法 */
            CtMethod newMethod = new CtMethod(ctMethods[i].getReturnType(), ctMethods[i].getName(),
                    ctMethods[i].getParameterTypes(), proxyCc);
            newMethod.setBody(methodBody);
            proxyCc.addMethod(newMethod);
        }

        proxyCc.writeFile("E:/tmp/basic");

        People people = new People("张三");
        return proxyCc.toClass().getConstructor(People.class).newInstance(people);
    }
}
