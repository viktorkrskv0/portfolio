package sw1.polynomials;

/**
 * Represents a multiplication of variables in a-z with an integral coefficient
 */
public class Monomial {

	/**
	 * @post this.getCoefficient() == coefficient
	 * @post for every v, 'a'<=v<='z', isVariable(v) == false
	 */

	private int[] variables;// array representing all the abc's. place 0 is the
							// power of a
							// place 1 is the power of b and so on
	private int coefficient;

	public Monomial(int coefficient) {
		// input: coefficient
		// output: constructor of a monom
		this.coefficient = coefficient;
		variables = new int[26];
		for (int i = 0; i < variables.length; i++) {
			variables[i] = 0;
		}
	}

	/**
	 * @return the coefficient of this monomial
	 */
	public int getCoefficient() {
		return this.coefficient;
	}

	/**
	 * @post getCoefficient() == coefficient
	 */
	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	/**
	 * @return true iff the input is a variable of this monomial (and appears in
	 *         toString).
	 */
	public boolean isVariable(char variable) {
		return this.variables[variable - 'a'] != 0;
	}

	/**
	 * @pre isVariable(variable)
	 * @return the degree of variable in this monomial
	 */
	public int getDegree(char variable) {
		return this.variables[variable - 'a'];
	}

	/**
	 * @pre degree >= 0
	 * @pre 'a'<=variable<='z'
	 * @post getDegree(variable) = degree
	 */
	public void setDegree(char variable, int degree) {
		this.variables[variable - 'a'] = degree;
	}

	/**
	 * @pre other!= null
	 * @return true iff the set of variables and the degree of each variable is
	 *         the same for this and other.
	 */
	public boolean hasSameDegrees(Monomial other) {
		boolean flag = true;
		int i = 0;
		while (flag && i < 26) {
			flag = this.variables[i] == other.getDegree((char) ('a' + i));
			i++;
		}
		return flag;
	}

	/**
	 * @pre assignment != null
	 * @pre assignment.length == 26
	 * @return the result of assigning assignment[0] to a, assignment[1] to b
	 *         etc., and computing the value of this Monomial
	 */
	public int evaluate(int[] assignment) {
		int evaluation = this.coefficient;
		for (int i = 0; i < this.variables.length; i++) {
			evaluation = evaluation * (int) Math.pow((double) assignment[i], (double) this.variables[i]);// flag:
																											// assumes
																											// that
																											// 0^0=
																											// 1
		}
		return evaluation;
	}

	/**
	 * Returns a string representation of this monomial by the mathematical
	 * convention. I.e., the coefficient is first (if not 1), then every
	 * variable in an alphabetic order followed by ^ and its degree (if > 1).
	 * For example, 13b^2x^3z
	 */
	public String toString() {
		String str = "";
		if (this.coefficient != -1 && this.coefficient != 1) {
			str = str + this.coefficient;
		}
		for (int i = 0; i < this.variables.length; i++) {
			if (this.variables[i] != 0) {
				if (this.variables[i] == 1) {
					str = str + (char) ('a' + i);
				} else {
					str = str + (char) ('a' + i) + "^" + this.variables[i];
				}
			}
		}
		return str;
	}

	/**
	 * Returns a "safe" copy of this monomial, i.e., if the copy is changed,
	 * this will not change and vice versa
	 */
	public Monomial getCopy() {
		Monomial copy = new Monomial(this.coefficient);
		copy.setCoefficient(this.coefficient);
		for (int i = 0; i < this.variables.length; i++) {
			copy.setDegree((char) ('a' + i), this.variables[i]);
		}
		return copy;
	}
}
