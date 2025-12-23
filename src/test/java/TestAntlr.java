import com.xx.UI.complex.textArea.content.segment.Paragraph;
import com.xx.antlr.CSV.imp.CsvListenerImp;
import com.xx.antlr.CSV.lexer.CSVLexer;

import com.xx.UI.complex.textArea.view.dataFormat.example.java.JavaImp.BlockEnumJava;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.lexer.Java20Lexer;
import com.xx.antlr.Json.imp.JsonListenerImp;
import com.xx.antlr.Json.lexer.JSONLexer;
import com.xx.antlr.CSV.parser.CSVParser;
import com.xx.antlr.Data.lexer.DataLexer;
import com.xx.antlr.Data.parser.DataParser;
import com.xx.antlr.Expr.ExprImp.EvalVisitor;
import com.xx.antlr.Expr.lexer.ExprLexer;
import com.xx.antlr.Expr.parser.ExprParser;
import com.xx.antlr.Json.parser.JSONParser;
import com.xx.antlr.Rows.lexer.RowsLexer;
import com.xx.antlr.Rows.parser.RowsParser;
import com.xx.antlr.XMLLexer.lexer.XMLLexer;
import com.xx.antlr.arrayInit.astImp.ArrayInitBaseListenerImp;
import com.xx.antlr.arrayInit.lexer.ArrayInitLexer;
import com.xx.antlr.arrayInit.parser.ArrayInitParser;
import com.xx.antlr.cymbol.imp.CymbolImp;
import com.xx.antlr.cymbol.lexer.cymbolLexer;
import com.xx.antlr.cymbol.parser.cymbolParser;
import com.xx.antlr.property.lexer.PropertyFileLexer;
import com.xx.antlr.property.parser.PropertyFileParser;
import com.xx.antlr.property.propertyImp.ProperFileListenerImp;
import com.xx.antlr.property.propertyImp.ProperFileVisitorImp;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestAntlr {
    @Test
    public void arrayInit() {

        // 使用字符串作为输入源
        String inputStr = "{99 , 3 , 451}";
        CharStream input = CharStreams.fromString(inputStr);

        ArrayInitLexer lexer = new ArrayInitLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArrayInitParser parser = new ArrayInitParser(tokens);

        ParseTree tree = parser.init();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new ArrayInitBaseListenerImp(), tree);
    }

    @Test
    public void expr() {
        String inputStr = """
                913
                a = 5
                b = 9
                a+b*2+3
                (1+2-b)*a+b
                """;
        CharStream input = CharStreams.fromString(inputStr);

        ExprLexer lexer = new ExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);

        ParseTree tree = parser.prog();
        EvalVisitor visitor = new EvalVisitor();
        visitor.visit(tree);
    }

    @Test
    public void rows() throws IOException {
        CharStream input = CharStreams.fromFileName("F:\\project\\java\\FXEditor\\src\\main\\java\\com\\xx\\Rows\\lexer\\t.txt");
        RowsLexer lexer = new RowsLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        int col = 3;
        RowsParser parser = new RowsParser(tokens, col);
        parser.setBuildParseTree(false);
        parser.file();
    }

    @Test
    public void data() throws IOException {
        CharStream input = CharStreams.fromFileName("src/main/java/com/xx/Data/lexer/t.text");
        DataLexer lexer = new DataLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DataParser parser = new DataParser(tokens);

        ParseTree tree = parser.file();
        System.out.println(tree.toStringTree(parser));
    }

    @Test
    public void testXMLLexer() throws IOException {
        CharStream input = CharStreams.fromFileName("src/main/java/com/xx/antlr/XMLLexer/lexer/t.xml");
        XMLLexer lexer = new XMLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();
        List<Token> tokenList = tokens.getTokens();

        for (Token token : tokenList) {
            // 跳过EOF之前的最后一个换行符（如果存在）
            if (token.getType() == Token.EOF) {
                System.out.println("[@" + token.getTokenIndex() + "," +
                        token.getStartIndex() + ":" + token.getStopIndex() +
                        "='<EOF>',<" + token.getType() + ">,"
                        + token.getLine() + ":" + token.getCharPositionInLine() + "]");
                break;
            }

            String tokenName = lexer.getVocabulary().getSymbolicName(token.getType());
            if (tokenName == null) {
                tokenName = String.valueOf(token.getType());
            }

            // 转义特殊字符：换行、回车、制表符
            String tokenText = token.getText()
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");

            // 匹配grun的输出格式
            System.out.println("[@" + token.getTokenIndex() + "," +
                    token.getStartIndex() + ":" + token.getStopIndex() +
                    "='" + tokenText + "',<" + tokenName + ">," +
                    token.getLine() + ":" + token.getCharPositionInLine() + "]");
        }
    }
    @Test
    public void testJavaLexer() throws IOException {
        CharStream input = CharStreams.fromFileName("src/main/java/com/xx/UI/complex/textArea/view/BDTextCell.java");
        Java20Lexer lexer = new Java20Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();
        List<Token> tokenList = tokens.getTokens();

        for (Token token : tokenList) {
            // 跳过EOF之前的最后一个换行符（如果存在）
            if (token.getType() == Token.EOF) {
                break;
            }

            BlockEnumJava type = BlockEnumJava.fromValue(token.getType());
            // 匹配grun的输出格式
            System.out.println("[@" + token.getTokenIndex() + "," +
                    token.getStartIndex() + ":" + token.getStopIndex() +
                    "='" + token.getText() + "',<" + type.name() + ">," +
                    token.getLine() + ":" + token.getCharPositionInLine() + "]");
        }

    }
    @Test
    public void csv() throws IOException {
        CharStream input = CharStreams.fromFileName("src/main/java/com/xx/antlr/CSV/t.csv");
        CSVLexer lexer = new CSVLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CSVParser parser = new CSVParser(tokens);
        ParseTree tree = parser.file();

        ParseTreeWalker walker = new ParseTreeWalker();
        CsvListenerImp listener = new CsvListenerImp();
        walker.walk(listener, tree);
        System.out.println(listener.getRows());
    }
    @Test
    public void json() throws IOException {
        CharStream input = CharStreams.fromFileName("src/main/java/com/xx/Json/t.json");
        JSONLexer lexer = new JSONLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        ParseTree tree = parser.json();

        ParseTreeWalker walker = new ParseTreeWalker();
        JsonListenerImp listener = new JsonListenerImp();
        walker.walk(listener,tree);
        System.out.println(listener.getXML(tree));

    }
    @Test
    public void propertyFile() throws IOException {
        CharStream input = CharStreams.fromFileName("src/main/java/com/xx/property/lexer/t.text");
        PropertyFileLexer lexer = new PropertyFileLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PropertyFileParser parser = new PropertyFileParser(tokens);

        ParseTree tree = parser.file();

//        监听器
        ParseTreeWalker walker = new ParseTreeWalker();
        ProperFileListenerImp imp = new ProperFileListenerImp();
        walker.walk(imp,tree);
        System.out.println(imp.getProps());
//        访问器
        ProperFileVisitorImp visitor = new ProperFileVisitorImp();
        visitor.visit(tree);
        System.out.println(visitor.getProps());
    }
    @Test
    public void cymbol() throws IOException {
        CharStream input = CharStreams.fromFileName("src/main/java/com/xx/cymbol/t.cymbol");
        cymbolLexer lexer = new cymbolLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        cymbolParser parser = new cymbolParser(tokens);

        ParseTree tree = parser.file();

        ParseTreeWalker walker = new ParseTreeWalker();
        CymbolImp listener = new CymbolImp();
        walker.walk(listener,tree);
        System.out.println(listener.getEdges());
    }

    @Test
    public void paragraph(){
        Paragraph paragraph = new Paragraph();
        paragraph.appendString("Hello, world!");
        paragraph.appendString("This is a test.");
        System.out.println(paragraph.getSegments().size());
    }
}
