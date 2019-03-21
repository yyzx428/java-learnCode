package beans.reflection.DataBinder;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.validation.DataBinder;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        Template template = new Template();
        DataBinder binder = new DataBinder(template);
        binder.setConversionService(new DefaultFormattingConversionService());
        MutablePropertyValues ps = new MutablePropertyValues();
        ps.add("name", "测试模板");
        ps.add("nums[0]", "1");
        ps.add("nums[1]", "2");
        binder.bind(ps);
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
