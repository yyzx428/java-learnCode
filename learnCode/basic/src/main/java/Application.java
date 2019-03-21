import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application {

    public static void main(String[] args) throws IOException {
//        File file = new File("C:\\Users\\29410\\Desktop\\ResultSql - 1.txt");
//        List<String> result ;
//        try (FileInputStream stream = new FileInputStream(file)) {
//            byte[] bytes = new byte[1000000];
//            stream.read(bytes);
//            String projectColumns = new String(bytes, "UTF-8");
//            result = Arrays.stream(projectColumns.split(System.lineSeparator())).map(String::trim).collect(Collectors.toCollection(LinkedList::new));
//        }
//        System.out.println(object2Json(result));
        abc(null);
    }

    public static void abc(Object a){
        System.out.println(((byte[])a)[0]);
    }

    public static String object2Json(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createGenerator(sw);
            objectMapper.writeValue(gen, o);
        } catch (IOException e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        } finally {
            if (null != gen) {
                try {
                    gen.close();
                } catch (IOException e) {
                    throw new RuntimeException("不能序列化对象为Json", e);
                }
            }
        }
        return sw.toString();
    }
}







