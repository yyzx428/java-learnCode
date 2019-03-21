package beans.standardBeanExpressionResolver.SimpleExpression;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.LinkedList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Any string'.length()");
        Integer length = (Integer) expression.getValue();
        System.out.println(length);

        Model model = new Model();
        model.setId("1111");
        model.setName("张三");

        expression = parser.parseExpression("name");
        EvaluationContext context = new StandardEvaluationContext(model);
        String name = expression.getValue(context, String.class);
        System.out.println(name);

        Models models = new Models();
        models.addModels(model);
        EvaluationContext setContext = new StandardEvaluationContext(models);
        parser.parseExpression("models[0].name").setValue(setContext, "李四");
        System.out.println(models.getModels().get(0).getName());

        SpelParserConfiguration configuration = new SpelParserConfiguration(true, true);
        ExpressionParser configurationParser = new SpelExpressionParser(configuration);
        configurationParser.parseExpression("models[1]").setValue(setContext, model);
        System.out.println(models.getModels().get(1).toString());
    }

    static class Models {
        private List<Model> models;

        public List<Model> getModels() {
            return models;
        }

        public void setModels(List<Model> models) {
            this.models = models;
        }

        public void addModels(Model model) {
            if (models == null) {
                models = new LinkedList<>();
            }
            models.add(model);
        }
    }

    static class Model {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Model{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
