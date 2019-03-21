package jdkUse.JMX;

public class People implements PeopleMBean {

    private String name;

    public People(String name) {
        this.name = name;
    }

    @Override
    public void sayHello(String name) {
        System.out.println(this.name+"say "+ name+" Hello!");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
