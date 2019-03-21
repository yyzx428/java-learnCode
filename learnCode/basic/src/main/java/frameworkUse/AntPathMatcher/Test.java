package frameworkUse.AntPathMatcher;

import org.springframework.util.AntPathMatcher;

/**
 * @description: ANT字符串匹配
 * @date: 2019/1/25
 */
public class Test {
    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static AntPathMatcher point = new AntPathMatcher(".");

    public static void main(String[] args) {

        System.out.println(antPathMatcher.isPattern("/asd/*"));

        System.out.println(antPathMatcher.combine("/file_{1}.jpg","1"));

        System.out.println(antPathMatcher.match("**/*.jpg","asdadad/sadad/asdadadada/1.jpg"));

        System.out.println(antPathMatcher.match("/**/*.jpg","/asdadad/sadad/asdadadada/1.jpg"));

        System.out.println(point.match("com.ssd.sds.*","com.ssd.sds.asda"));
    }
}
