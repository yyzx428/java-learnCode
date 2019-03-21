package yaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

public class Test {
    private static final String filePath = System.getProperty("user.dir") + "\\basic\\src\\main\\java\\yaml\\test.yml";

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
        abc yaml = new Yaml(new Constructor(abc.class)).loadAs(inputStreamReader, abc.class);
        System.out.println(yaml);
    }


}

