package frameworkUse.mybatis.tokenParser;


import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

public class MybatisTokenParserTest {
    public static void main(String[] args) {
        GenericTokenParser parser=new GenericTokenParser("#{", "}", new TokenHandler() {
            @Override
            public String handleToken(String content) {
                return null;
            }
        });

        System.out.println(parser.parse("asdadads#{asdada}"));
    }
}
