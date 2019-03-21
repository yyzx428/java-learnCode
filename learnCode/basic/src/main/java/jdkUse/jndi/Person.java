package jdkUse.jndi;

import java.io.Serializable;
import java.rmi.Remote;

public class Person implements Remote,Serializable {
    private static final long serialVersionUID = -8592182872966400365L;


    private String name;
    private String pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "jdkUse.jndi.Person{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
