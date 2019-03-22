package beans.conversionServiceUse;

import com.google.common.collect.Lists;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        DefaultConversionService cs = new DefaultConversionService();

        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5);
        List<String> convert = (List<String>) cs.convert(input,
                TypeDescriptor.forObject(input),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class)));
        convert.forEach(System.out::println);
    }
}
