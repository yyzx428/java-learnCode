//package antlr;
//
//import antlr.antlrcode.HelloLexer;
//import antlr.antlrcode.HelloParser;
//import org.antlr.v4.runtime.CharStreams;
//import org.antlr.v4.runtime.CommonTokenStream;
//import org.antlr.v4.runtime.tree.ParseTree;
//
//public class Test {
//    public static void main(String[] args) {
//
//        HelloLexer lexer = new HelloLexer(CharStreams.fromString("Hello sssasd"));
//
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//
//        HelloParser parser = new HelloParser(tokens);
//
//        ParseTree tree = parser.r();
//
//        System.out.println(tree.toStringTree(parser));
//    }
//}
