package jdkUse.JMX;

import com.alibaba.druid.pool.DruidDataSourceMBean;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static jdkUse.JMX.HelloAgent.serverUrl;

public class CommonGetMBean {
    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL(serverUrl);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        Set<ObjectInstance> MBeanset = mbsc.queryMBeans(null, null);
        Set<ObjectNameEntity> ObjectNameEntity = new HashSet<>(MBeanset.size());
        for (ObjectInstance entity : MBeanset) {
            System.out.println("getClassName:" + entity.getClassName());
            try {
                Class<?> classes = Class.forName(entity.getClassName(), false, Thread.currentThread().getContextClassLoader());
                Stream.of(classes.getInterfaces()).forEach(System.out::println);
                Class<?> mBeanClass = getMBeanInterface(classes);
                if (mBeanClass == null) continue;
                System.out.println("Class:" + mBeanClass);
                System.out.println("getObjectName:" + entity.getObjectName());
                ObjectNameEntity.add(new ObjectNameEntity(mBeanClass, entity.getObjectName()));
            } catch (ClassNotFoundException e) {
                System.out.println(e.getCause() + " :" + "类不存在");
            }
        }

        Set<Object> objects = new HashSet<>(ObjectNameEntity.size());

        for (ObjectNameEntity entity : ObjectNameEntity) {
            try {
                objects.add(getObject(entity.getTcalss(), mbsc, entity.getObjectName()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        objects.stream().filter(x -> x instanceof DruidDataSourceMBean)
                .map(DruidDataSourceMBean.class::cast).forEach(x -> System.out.println(x.getObjectName()));
    }

    public static <T> T getObject(Class<T> tClass, MBeanServerConnection mbsc, ObjectName mbeanName) {
        return MBeanServerInvocationHandler.newProxyInstance(mbsc, mbeanName, tClass, false);
    }

    public static Class<?> getMBeanInterface(Class<?> calsses) {

        if (calsses.getInterfaces() != null) {
            for (Class<?> classe : calsses.getInterfaces()) {
                if (classe.getName().contains("MBean")) {
                    return classe;
                }
                getMBeanInterface(classe);
            }
        }

        if (calsses.getSuperclass() != null && calsses.getSuperclass().getInterfaces() != null) {
            for (Class<?> classe : calsses.getSuperclass().getInterfaces()) {
                if (classe.getName().contains("MBean")) {
                    return classe;
                }
                getMBeanInterface(classe);
            }
        }

        return ModelMBean.class;
    }


    static class ObjectNameEntity {
        private Class<?> tcalss;
        private ObjectName objectName;

        public ObjectNameEntity(Class<?> tcalss, ObjectName objectName) {
            this.tcalss = tcalss;
            this.objectName = objectName;
        }

        public Class<?> getTcalss() {
            return tcalss;
        }

        public void setTcalss(Class<?> tcalss) {
            this.tcalss = tcalss;
        }

        public ObjectName getObjectName() {
            return objectName;
        }

        public void setObjectName(ObjectName objectName) {
            this.objectName = objectName;
        }
    }
}
