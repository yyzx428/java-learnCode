package yaml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class abc {
    private Map map;
    private String a;
    private Integer b;
    private List<String> names;

    private List<List<Integer>> nameList;

    private String nullStr;
}
