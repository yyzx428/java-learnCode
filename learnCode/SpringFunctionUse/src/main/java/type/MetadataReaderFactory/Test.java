package type.MetadataReaderFactory;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        MetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        MetadataReader metadataReader = factory.getMetadataReader("type.MetadataReaderFactory.Test$Template");
        System.out.println(metadataReader.getResource().isOpen());
    }

    static class Template{

    }
}
