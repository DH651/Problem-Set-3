/**
 * 
 */
package expressivo;

import java.util.Map;

/**
 * An immutable data type representing an expression containing binary
 * operations: +, -, *, / with operands as nonnegative integers, floating-point
 * numbers and variables (case-sensitive nonempty strings of letters)
 */
public abstract class BinaryOperation implements Expression {

    // fields
    protected final Expression left;
    protected final Expression right;

    // Abstraction Function:
    // AF(left, right) = An expression containing binary operations like +, -, *, /
    // with this.left and this.right are left and right operand containing
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
    BinaryOperation(Expression left, Expression right) {
	this.left = left;
	this.right = right;
    }

    @Override
    public String toString() {
	// compare the precedence of this with the precedence of left and right operand
	// if lower, encapsulate toString in parenthesis, otherwise don't
	// implemented separately in each subclass
	throw new RuntimeException("unimplemented");
    }

    @Override
    public boolean equals(Object thatObject) {
	// If outer operation of this expression and thatObject are same and their left
	// and right operand are also
	// equals then both the both the objects are equals, otherwise false
	if (thatObject instanceof BinaryOperation && this.getClass() == thatObject.getClass()) {
	    BinaryOperation anotherBinaryOperation = (BinaryOperation) thatObject;

	    return left.equals(anotherBinaryOperation.getLeftOperand())
		    && right.equals(anotherBinaryOperation.getRightOperand());
	} else {
	    return false;
	}

    }

    @Override
    public Expression derivative(String variable) {
	throw new RuntimeException("unimplemented");
    }

    @Override
    public Expression simplify(Map<String, Double> mapping) {
	throw new RuntimeException("unimplemented");
    }

    @Override
    public int getprecedenceDisparity(String operator) {
	// implemented separately in each subclass
	//
	throw new RuntimeException("unimplemented");
    }

    @Override
    public boolean isNumber() {
	return left.isNumber() && right.isNumber();
    }

    /**
     * Returns the left operand of the outer operation of this polynomial expression
     * 
     * @return left operand of the outer operation of this polynomial expression
     */
    private Expression getLeftOperand() {
	return this.left;
    }

    /**
     * Returns the right operand of the outer operation of this polynomial
     * expression
     * 
     * @return right operand of the outer operation of this polynomial expression
     */
    private Expression getRightOperand() {
	return this.right;
    }

}
