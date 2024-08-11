package expressivo.parser;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class GrammerVisualizer {

    public static void main(String[] args) {
	try {
	    // Load your grammar file
	    String input = "67 * y * (z / 4.666)"; // Example input

	    // Set up input stream
	    CharStream charStream = CharStreams.fromString(input);

	    // Initialize lexer and parser
	    ExpressionLexer lexer = new ExpressionLexer(charStream);
	    lexer.reportErrorsAsExceptions();

	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    ExpressionParser parser = new ExpressionParser(tokens);
	    parser.reportErrorsAsExceptions();

	    // Parse the input
	    ParseTree tree = parser.root();

	    System.err.println(tree.toStringTree(parser));
	    Trees.inspect(tree, parser);

	    // Initialize ExpressionBaseListener and create abstract syntax tree
	    System.out.println();

	    ExpressionBaseListener expressionListener = new ExpressionBaseListener();
	    new ParseTreeWalker().walk(expressionListener, tree);
	    System.out.println(expressionListener.getExpression());

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
