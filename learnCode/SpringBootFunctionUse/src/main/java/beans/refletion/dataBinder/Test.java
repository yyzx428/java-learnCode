package beans.refletion.dataBinder;


import org.springframework.beans.MutablePropertyValues;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.handler.IgnoreTopLevelConverterNotFoundBindHandler;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        BindHandler bandHandler = new IgnoreTopLevelConverterNotFoundBindHandler();
        Template template = new Template();
        StandardEnvironment environment = new StandardEnvironment();
        HashMap<String,Object> ps = new HashMap<>(4);
        ps.put("template.name","测试");
        ps.put("template.nums[0]","1");
        ps.put("template.nums[1]","2");
        environment.getPropertySources().addLast(new MapPropertySource("user",ps));
        Binder.get(environment).bind("template", Bindable.ofInstance(template), bandHandler);
        System.out.println(template);
    }

    static class Template {
        private String name;
        private List<Integer> nums;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Integer> getNums() {
            return nums;
        }

        public void setNums(List<Integer> nums) {
            this.nums = nums;
        }

        @Override
        public String toString() {
            return "Template{" +
                    "name='" + name + '\'' +
                    ", nums=" + nums +
                    '}';
        }
    }
}
