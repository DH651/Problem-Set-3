// Generated from Expression.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import expressivo.*;
import java.util.Stack;

/**
 * This class provides an empty implementation of {@link ExpressionListener},
 * which can be extended to create a listener which only needs to handle a
 * subset of the available methods.
 */
public class ExpressionBaseListener implements ExpressionListener {
    private Stack<Expression> stack = new Stack<>();
    // Invariant: stack contains the IntegerExpression value of each parse
    // subtree that has been fully-walked so far, but whose parent has not yet
    // been exited by the walk. The stack is ordered by recency of visit, so that
    // the top of the stack is the IntegerExpression for the most recently walked
    // subtree.
    //
    // At the start of the walk, the stack is empty, because no subtrees have
    // been fully walked.
    //
    // Whenever a node is exited by the walk, the IntegerExpression values of its
    // children are on top of the stack, in order with the last child on top. To
    // preserve the invariant, we must pop those child IntegerExpression values
    // from the stack, combine them with the appropriate IntegerExpression
    // producer, and push back an IntegerExpression value representing the entire
    // subtree under the node.
    //
    // At the end of the walk, after all subtrees have been walked and the the
    // root has been exited, only the entire tree satisfies the invariant's
    // "fully walked but parent not yet exited" property, so the top of the stack
    // is the IntegerExpression of the entire parse tree.

    /**
     * Returns the expression constructed by this listener object. Requires that
     * this listener has completely walked over an IntegerExpression parse tree
     * using ParseTreeWalker.
     * 
     * @return IntegerExpression for the parse tree that was walked
     */
    public Expression getExpression() {
	return stack.get(0);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterRoot(ExpressionParser.RootContext ctx) {
	// doesn't do anything
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitRoot(ExpressionParser.RootContext ctx) {
	// do nothing, root has only one child and it is already on the top of the stack

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterExpression(ExpressionParser.ExpressionContext ctx) {
	// don't do anything
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitExpression(ExpressionParser.ExpressionContext ctx) {

	if (ctx.term() != null) {
	    // matched the expression : term rule
	    // do nothing as the term value is already on the stack

	} else if (ctx.OP1() != null) {
	    // matched the expression : expression OP1 expression ruke
	    Expression right = stack.pop();
	    Expression left = stack.pop();
	    String operand = ctx.OP1().getText();
	    stack.add(createExpression(operand, left, right));

	} else if (ctx.OP2() != null) {
	    // matched the expression : expression OP2 expression rule
	    Expression right = stack.pop();
	    Expression left = stack.pop();
	    String operand = ctx.OP2().getText();
	    stack.add(createExpression(operand, left, right));

	} else {
	    // matched the '(' expression ')' rule
	    // do nothing because expression's value is already on the stack
	}

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterTerm(ExpressionParser.TermContext ctx) {
	// doesn't do anything
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitTerm(ExpressionParser.TermContext ctx) {
	// matched expression : term rule
	if (ctx.CONSTANT() != null) {
	    Expression constant = new expressivo.Number(ctx.CONSTANT().getText());
	    stack.add(constant);
	}
	if (ctx.VARIABLE() != null) {
	    Expression variable = new expressivo.Variable(ctx.VARIABLE().getText());
	    stack.add(variable);
	}
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
	// doesn't do anything
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
	// doesn't do anything
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void visitTerminal(TerminalNode node) {
	// doesn't do anything
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void visitErrorNode(ErrorNode node) {
	// doesn't do anything
    }

    /**
     * Creates an expression from given operator and the left and right operands
     * 
     * @param operator, an operator, must be on of '+', '-', '*', '/'
     * @param left,     an expression
     * @param right,    an expression
     * @return a binary operation
     */
    private Expression createExpression(String operator, Expression left, Expression right) {
	if (operator.equals("+")) {
	    return new expressivo.Addition(left, right);
	} else if (operator.equals("-")) {
	    return new expressivo.Subtraction(left, right);
	} else if (operator.equals("*")) {
	    return new expressivo.Multiplication(left, right);
	} else {
	    return new expressivo.Division(left, right);
	}
    }
}