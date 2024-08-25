/**
 * 
 */
package expressivo;

import java.util.Map;

/**
 * An immutable data type representing a monomial expression containing a
 * variable (case-sensitive nonempty strings of letters)
 *
 */
public class Variable implements Expression {
    // fields
    private final String variable;

    // Abstraction Function:
    // AF(variable) = A monomial expression that contains a variable

    // Rep Invariant:
    // variable doesn't point to null
    // variable is a non-empty string

    // Safety from rep exposure:
    // All the fields private and final
    // Observers like toString, hashCode, equals don't expose representation to the
    // client
    // Mutators like derivative, simplify don't expose representation to the client

    // constructor
    public Variable(String variable) {
	this.variable = variable;
    }

    @Override
    public String toString() {
	return variable.toString();

    }

    @Override
    public boolean equals(Object thatObject) {
	if (thatObject instanceof Expression && thatObject instanceof Variable) {
	    Variable anotherVariable = (Variable) thatObject;
	    return sameValue(anotherVariable);
	}
	return false;
    }

    @Override
    public int hashCode() {
	return variable.hashCode();
    }

    @Override
    public Expression derivative(String variable) {
	if (this.variable.equals(variable)) {
	    return new Number(1);
	} else {
	    return new Number(0);
	}
    }

    @Override
    public Expression simplify(Map<String, Double> mapping) {
	if (mapping.containsKey(variable)) {
	    Double number = mapping.get(variable);
	    return new Number(number.floatValue());
	} else {
	    return new Variable(variable);
	}
    }

    @Override
    public int getprecedenceDisparity(String operator) {
	return 1;
    }

    @Override
    public boolean isNumber() {
	return false;
    }

    /**
     * Compares two monomial representing variables and returns true if both of them
     * are equal
     * 
     * @param thatObject, a monomial representing a integer or floating-point number
     * @return true if thatObject is equal to this monomial
     */
    private boolean sameValue(Variable thatObject) {
	return variable.toString().equals(thatObject.toString());

    }
}
