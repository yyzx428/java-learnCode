package jdkUse.JMX;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloAgent {
    public final static String serverUrl = "service:jmx:rmi:///jdkUse.jndi/rmi://localhost:1099/jmxrmi";
    public final static String mBeanName = "MyMBean:name=HelloWorld";
    public final static String mName = "MyMBean";
    public static void main(String[] args) throws IOException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        // 为MBean（下面的new Hello()）创建ObjectName实例
        //等同于 ObjectName helloName = new ObjectName(mBeanName);
        ObjectName helloName = new ObjectName(mName,"name","HelloWorld");

        // 将new Hello()这个对象注册到MBeanServer上去
        server.registerMBean(new People("张三"), helloName);

        //这句话非常重要，不能缺少！注册一个端口，绑定url后，客户端就可以使用rmi通过url方式来连接JMXConnectorServer
        Registry registry = LocateRegistry.createRegistry(1099);

        //构造JMXServiceURL
        JMXServiceURL jmxServiceURL = new JMXServiceURL(serverUrl);
        //创建JMXConnectorServer
        JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, server);
        //启动
        cs.start();
    }
}
