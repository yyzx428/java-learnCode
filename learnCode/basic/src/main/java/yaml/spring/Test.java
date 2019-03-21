package yaml.spring;

import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.reader.UnicodeReader;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class Test {
    private static final String filePath = System.getProperty("user.dir") + "\\basic\\src\\main\\java\\yaml\\test.yml";

    public static void main(String[] args) throws FileNotFoundException {
        Yaml yaml = new Yaml(new StrictMapAppenderConstructor(), new Representer(),
                new DumperOptions(), new Resolver() {
            @Override
            public void addImplicitResolver(Tag tag, Pattern regexp,
                                            String first) {
                if (tag == Tag.TIMESTAMP) {
                    return;
                }
                super.addImplicitResolver(tag, regexp, first);
            }
        });

        UnicodeReader reader = new UnicodeReader(new FileInputStream(new File(filePath)));
        Map<String, Object> map;
        for (Object object : yaml.loadAll(reader)) {
            if (object != null) {
                map = getFlattenedMap(asMap(object));

                System.out.println(map);
            }
        }
    }

    protected static final Map<String, Object> getFlattenedMap(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        buildFlattenedMap(result, source, null);
        return result;
    }

    private static void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, String path) {
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.hasText(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                } else {
                    key = path + '.' + key;
                }
            }
            Object value = entry.getValue();
            if (value instanceof String) {
                result.put(key, value);
            } else if (value instanceof Map) {
                // Need a compound key
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) value;
                buildFlattenedMap(result, map, key);
            } else if (value instanceof Collection) {
                // Need a compound key
                @SuppressWarnings("unchecked")
                Collection<Object> collection = (Collection<Object>) value;
                int count = 0;
                for (Object object : collection) {
                    buildFlattenedMap(result,
                            Collections.singletonMap("[" + (count++) + "]", object), key);
                }
            } else {
                result.put(key, (value != null ? value : ""));
            }
        }
    }

    private static Map<String, Object> asMap(Object object) {
        // YAML can have numbers as keys
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        if (!(object instanceof Map)) {
            // A document can be a text literal
            result.put("document", object);
            return result;
        }

        Map<Object, Object> map = (Map<Object, Object>) object;
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = asMap(value);
            }
            Object key = entry.getKey();
            if (key instanceof CharSequence) {
                result.put(key.toString(), value);
            } else {
                // It has to be a map key in this case
                result.put("[" + key.toString() + "]", value);
            }
        }
        return result;
    }

    protected static class StrictMapAppenderConstructor extends Constructor {

        // Declared as public for use in subclasses
        public StrictMapAppenderConstructor() {
            super();
        }

        @Override
        protected Map<Object, Object> constructMapping(MappingNode node) {
            try {
                return super.constructMapping(node);
            } catch (IllegalStateException ex) {
                throw new ParserException("while parsing MappingNode",
                        node.getStartMark(), ex.getMessage(), node.getEndMark());
            }
        }

        @Override
        protected Map<Object, Object> createDefaultMap() {
            final Map<Object, Object> delegate = super.createDefaultMap();
            return new AbstractMap<Object, Object>() {
                @Override
                public Object put(Object key, Object value) {
                    if (delegate.containsKey(key)) {
                        throw new IllegalStateException("Duplicate key: " + key);
                    }
                    return delegate.put(key, value);
                }

                @Override
                public Set<Entry<Object, Object>> entrySet() {
                    return delegate.entrySet();
                }
            };
        }
    }
}
