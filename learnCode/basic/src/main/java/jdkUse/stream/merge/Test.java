package jdkUse.stream.merge;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        Map<String, Values> map1 = new HashMap<>();
        map1.put("aaa", new Values(1, 10));
        map1.put("bbb", new Values(5, 50));
        map1.put("ccc", new Values(2, 30));

        Map<String, Values> map2 = new HashMap<>();
        map2.put("aaa", new Values(2, 20));
        map2.put("bbb", new Values(3, 50));
        map2.put("ccc", new Values(3, 10));

        Map<String, Values> result = mergeMaps(Lists.newArrayList(map1,map2));

        System.out.println(result);
    }

    public static Map<String, Values> mergeMaps(List<Map<String, Values>> maps) {
        return maps.stream()
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> new Values(v1.getCount() + v2.getCount(), v1.getValue() + v2.getValue())));
    }

    @Data
    @AllArgsConstructor
    private static class Values {
        private int count;
        private int value;
    }
}


