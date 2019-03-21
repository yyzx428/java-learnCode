package beans.reflection.BeanWrapperImpl;

import org.springframework.beans.BeanWrapperImpl;

public class Test {
    public static void main(String[] args) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(Template.class);
        wrapper.setPropertyValue("name", "张三");
        System.out.println(wrapper.getPropertyValue("name"));
    }

    static class Template {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
