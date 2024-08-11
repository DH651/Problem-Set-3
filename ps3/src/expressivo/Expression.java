/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionParser;
import expressivo.parser.ExpressionBaseListener;

/**
 * An immutable data type representing a polynomial expression of: + and *
 * nonnegative integers and floating-point numbers variables (case-sensitive
 * nonempty strings of letters)
 * 
 * <p>
 * PS3 instructions: this is a required ADT interface. You MUST NOT change its
 * name or package or the names or type signatures of existing methods. You may,
 * however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {

    // Data-type definition
    // Expression = Variable(name:String) + Number(number:Integer)
    // + BinaryOperation(left:Expression, right:Expression)

    /**
     * Parse an expression.
     * 
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
	// Create a stream of characters from the input stream.
	CharStream stream = CharStreams.fromString(input);

	// Initialize lexer
	ExpressionLexer lexer = new ExpressionLexer(stream);
	lexer.reportErrorsAsExceptions();

	// Create a stream of tokens.
	CommonTokenStream tokens = new CommonTokenStream(lexer);

	// Initialize parser
	ExpressionParser parser = new ExpressionParser(tokens);
	parser.reportErrorsAsExceptions();

	// Generate the parser tree using the starting rule
	// root is the starting rule for Expression grammar
	ParseTree tree = parser.root();

	// *** Debugging option #1: print the tree to the console
	System.err.println(tree.toStringTree(parser));

	// *** Debugging option #2: show the tree in a window
	Trees.inspect(tree, parser);

	// Initialize ExpressionBaseListener and create abstract syntax tree

	ExpressionBaseListener listener = new ExpressionBaseListener();
	ParseTreeWalker walker = new ParseTreeWalker();
	walker.walk(listener, tree);
	return listener.getExpression();
    }

    /**
     * Returns a parsable representation of this expression with the the the format:
     * (left-operand whitespace operator whitespace right-operand) where
     * left-operand and right-operand are expressions
     * 
     * @return a parsable representation of this expression, such that for all
     *         e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);

    /**
     * @return hash code value consistent with the equals() definition of structural
     *         equality, such that for all e1,e2:Expression, e1.equals(e2) implies
     *         e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();

    // more instance methods
    /**
     * Differentiates this expression with respect to the given variable and returns
     * the derivative of this expression with respect to the given variable.
     * 
     * @param variable, any variable
     * @return derivative of this expression with respect to the given variable
     */
    public Expression derivative(String variable);

    /**
     * Attempts to simplify this expression and returns a substituted polynomial
     * produced by substituting values for variables from the given environment
     * (mapping of variable to values). If an expression contains constants and no
     * variables then simplification reduces it to a single number. Variables from
     * this expression that are not present in the environment are not changed while
     * the variables that are in the environment but are not present in this
     * expression are ignored.
     * 
     * @param environment,a mapping of variables to values, the set of variables in
     *                      the environment may be different than the set of
     *                      variables found in this expression.
     * @return a substituted polynomial containing values for variables from the
     *         given environment.
     */
    public Expression simplify(Map<String, Double> mapping);

    /**
     * Compares the precedence of the the given operator with the precedence of the
     * outer operation of this expression. If the precedence of the outer operation
     * of this expression is higher, return one; if lower, return minus one;
     * otherwise, return zero.
     * 
     * @param operator, whose precedence is to be compared with, must be one of "*",
     *                  "/", "+", "-"
     * @return returns one if the precedence of the outer operation of this
     *         expression is higher, returns minus one if lower, otherwise, return
     *         one
     */
    public int getprecedenceDisparity(String operator);

    /**
     * Checks if this expression is a monomial expression representing a integer
     * number or a floating-point number
     * 
     * @return returns true, if this expression is a monomial expression
     *         representing a integer or a floating number, otherwise returns false
     */
    public boolean isNumber();

}
