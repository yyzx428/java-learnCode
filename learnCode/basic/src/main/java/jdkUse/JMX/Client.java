package jdkUse.JMX;


import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import static jdkUse.JMX.HelloAgent.mBeanName;
import static jdkUse.JMX.HelloAgent.serverUrl;

public class Client {
    public static void main(String[] args) throws IOException, MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, InvalidAttributeValueException, IntrospectionException {
        JMXServiceURL url = new JMXServiceURL(serverUrl);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        ObjectName mbeanName = new ObjectName(mBeanName);

        //print domains
        System.out.println("Domains:---------------");
        String domains[] = mbsc.getDomains();
        for (int i = 0; i < domains.length; i++) {
            System.out.println("Domain[" + i + "] = " + domains[i]);
        }
        System.out.println();

        //MBean count
        System.out.println("MBean count:---------------");
        System.out.println("MBean count = " + mbsc.getMBeanCount());
        System.out.println();

        //process attribute
        System.out.println("process attribute:---------------");
        mbsc.setAttribute(mbeanName, new Attribute("Name", "张三")); //set value
        System.out.println("Name = " + mbsc.getAttribute(mbeanName, "Name")); //get value
        System.out.println();

        //invoke via proxy
        System.out.println("invoke via proxy:---------------");
        PeopleMBean proxy = MBeanServerInvocationHandler.newProxyInstance(mbsc, mbeanName, PeopleMBean.class, false);
        proxy.sayHello("李四");
        System.out.println();

        //invoke via rmi
        System.out.println("invoke via rmi:---------------");
        System.out.println(mbsc.invoke(mbeanName, "sayHello", new Object[] { "李四" }, new String[] { String.class.getName() }));
        System.out.println();

        //get mbean information
        System.out.println("get mbean information:---------------");
        MBeanInfo info = mbsc.getMBeanInfo(mbeanName);
        System.out.println("Hello Class:" + info.getClassName());
        System.out.println("Hello Attribute:" + info.getAttributes()[0].getName());
        System.out.println("Hello Operation:" + info.getOperations()[0].getName());
        System.out.println();

        //ObjectName of MBean
        System.out.println("ObjectName of MBean:---------------");
        Set set = mbsc.queryMBeans(null, null);
        for (Iterator it = set.iterator(); it.hasNext();) {
            ObjectInstance oi = (ObjectInstance)it.next();
            System.out.println(oi.getObjectName());
        }
    }
}
