package jdkUse.jndi;

import org.springframework.jndi.JndiTemplate;

import javax.naming.Context;
import javax.naming.NamingException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Date;
import java.util.Properties;

public class JndiDemo {

    public static void main(String[] args) throws RemoteException {
        //创建本地端口
        LocateRegistry.createRegistry(1099);
        //注册jndi方法
        Properties enc = new Properties();
        enc.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        enc.setProperty(Context.PROVIDER_URL, "rmi://localhost:1099");
        JndiTemplate jndiTemplate = new JndiTemplate(enc);
        try {
            Context context = jndiTemplate.getContext();
            context.bind("java:comp/env/systemStartTime", new RemoteDate());
            context.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        //调用jndi方法
        try {
            RemoteDate date=jndiTemplate.lookup("java:comp/env/systemStartTime",RemoteDate.class);
            System.out.println(date.toString());
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    static class RemoteDate extends Date implements Remote {
    }

}
