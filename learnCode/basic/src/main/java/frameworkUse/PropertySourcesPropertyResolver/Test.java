package frameworkUse.PropertySourcesPropertyResolver;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 字符串占位
 * @date: 2019/1/25
 */
public class Test {
    private static PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(null);

    private static StandardEnvironment environment = new StandardEnvironment();

    static {
        resolver.setConversionService(new DefaultFormattingConversionService());
        resolver.setIgnoreUnresolvableNestedPlaceholders(true);
        resolver.setPlaceholderPrefix("${");
        resolver.setPlaceholderSuffix("}");
        resolver.setValueSeparator(":");


        environment.setConversionService(new DefaultFormattingConversionService());
        environment.setIgnoreUnresolvableNestedPlaceholders(true);
        environment.setPlaceholderPrefix("${");
        environment.setPlaceholderSuffix("}");
        environment.setValueSeparator(":");
    }


    public static void main(String[] args) {
        Map map = new HashMap<>();
        environment.getPropertySources().addBefore("systemEnvironment",
                new MapPropertySource("customProperties",map));

        map.put("abc",1);

        System.out.println(environment.getRequiredProperty("abc",Integer.class));

        System.out.println(environment.resolvePlaceholders("${user.dir}"));

    }


}
