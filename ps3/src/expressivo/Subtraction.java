package expressivo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * An immutable data type representing an expression containing binary
 * operations: - with operands as nonnegative integers, floating-point numbers
 * and variables (case-sensitive nonempty strings of letters)
 */
public class Subtraction extends BinaryOperation {
    // fields

    // Abstraction Function:
    // AF(left, right, operator) = An expression containing subtraction of this.left
    // and this.right
    // where are this.left and this.right operand contain
    // non-negative integers, floating-point numbers
    // and variables (case-sensitive nonempty strings of letters)

    // Rep Invariant:
    // left and right operand don't point to null

    // Safety from rep exposure:
    // Both the left and right operands are private and final
    // Observers like toString, hashCode, equals don't expose representation to the
    // client
    // Mutators like derivative, simplify don't expose representation to the client

    // constructor
    public Subtraction(Expression left, Expression right) {
	super(left, right);
    }

    @Override
    public String toString() {
	// Here precedence is defined by PEMDAS rule

	// If left operand has lower precedence, wrap it in parenthesis
	String result = "";
	if (this.left.getprecedenceDisparity("-") < 0) {
	    result += String.format("(%s)", left);
	} else {
	    result += String.format("%s", left);
	}
	result += " - "; // add operator with whitespace before and after it to the result.
	// If right operand has lower precedence, wrap it in parenthesis
	if (this.right.getprecedenceDisparity("-") <= 0) {
	    result += String.format("(%s)", right);
	} else {
	    result += String.format("%s", right);
	}
	return result;
    }

    @Override
    public int hashCode() {
	return left.hashCode() - right.hashCode();
    }

    @Override
    public Expression derivative(String variable) {
	Expression newLeftOperand = left.derivative(variable); // d(u) / dx
	Expression newRightOperand = right.derivative(variable); // d(v) / dx

	// d(u) / dx - d(v) / dx
	Expression derivativeBeforeSimplify = new Subtraction(newLeftOperand, newRightOperand);

	// simplify
	Expression derivativeAfterSimplify = derivativeBeforeSimplify.simplify(new HashMap<>());

	return derivativeAfterSimplify;
    }

    @Override
    public Expression simplify(Map<String, Double> mapping) {
	Expression simplifiedLeftOperand = left.simplify(mapping);
	Expression simplifiedRightOperand = right.simplify(mapping);
	Expression zero = new Number(0);

	if (simplifiedLeftOperand.isNumber() && simplifiedRightOperand.isNumber()) {
	    BigDecimal number1 = new BigDecimal(simplifiedLeftOperand.toString());
	    BigDecimal number2 = new BigDecimal(simplifiedRightOperand.toString());
	    // Constant - Constant = Constant
	    BigDecimal result = number1.subtract(number2);
	    BigDecimal resultRounded = result.setScale(5, RoundingMode.HALF_UP);
	    return new Number(resultRounded.floatValue());

	} else if (simplifiedRightOperand.equals(zero)) {
	    // Expression - 0 = Expression
	    return simplifiedLeftOperand;

	} else {

	    return new Subtraction(simplifiedLeftOperand, simplifiedRightOperand);
	}
    }

    @Override
    public int getprecedenceDisparity(String operator) {
	if (operator == "+" || operator == "-") {
	    return 0;
	} else if (operator == "*" || operator == "/") {
	    return -1;
	} else {
	    throw new IllegalArgumentException("The given operation must be one of *, +, -, /");
	}
    }

}
