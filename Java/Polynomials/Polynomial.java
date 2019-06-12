package sw1.polynomials;

public class Polynomial {

	private Monomial[] monomials;

	/**
	 * Creates a polynomial with (safe copies of) the given monomials
	 * 
	 * @pre monomials != null
	 * @pre for all i, 0 <= i < monmials.length : monomials[i] != null
	 * @post for all i, 0 <= i < monmials.length : monomials[i].getCoefficient()
	 *       == getMonomial(i).getCoefficient()
	 * @post for all i,v, 0 <= i < monmials.length, 'a'<=v<='z' :
	 *       monomials[i].getDegree(v) == getMonomial(i).getDegree(v)
	 */
	public Polynomial(Monomial[] monomials) {
		this.monomials = monomials;
	}

	/**
	 * @return the number of monomials in this polynomial
	 */
	public int getMonomialCount() {
		return this.monomials.length; // flag: assumes there couldn't be any
										// changes in the array of monomials
	}

	/**
	 * @pre 0<=index < getMonomialCount()
	 * @return a safe copy of the monomial at the given index
	 */
	public Monomial getMonomial(int index) {
		return this.monomials[index].getCopy();
	}

	/**
	 * @pre other != null
	 * @post Creates a new Polynomial which is the sum of this polynomial and
	 *       other. E.g., the sum of 13b^2x^3z+15 and -4b^2x^3z is 9b^2x^3z+15
	 */
	public Polynomial add(Polynomial other) {
		Monomial[] Monomials = new Monomial[this.getMonomialCount() + other.getMonomialCount()];
		for (int i = 0; i < this.getMonomialCount(); i++) {
			Monomials[i] = this.getMonomial(i).getCopy();
		}

		for (int i = 0; i < other.getMonomialCount(); i++) {
			Monomial m = other.getMonomial(i);
			int isInIndex = isIn(m, Monomials);
			if (isInIndex != -1) {
				joinCoefficients(Monomials, m, isInIndex);
			} else {
				addToArray(Monomials, m);
			}
		}

		int counter = 0;
		for (int i = 0; i < Monomials.length; i++) {
			if (Monomials[i] != null && Monomials[i].getCoefficient() != 0) {
				counter++;
			}
		}

		Monomial[] newM = new Monomial[counter];
		counter = 0;
		for (int i = 0; i < Monomials.length; i++) {
			if (Monomials[i] != null && Monomials[i].getCoefficient() != 0) {
				newM[counter] = Monomials[i];
				counter++;
			}
		}

		Polynomial p = new Polynomial(newM);
		return p;
	}

	public int isIn(Monomial m, Monomial[] mArray) {
		// input:monom and an array of monoms
		// output: the index i such that mArray[i].hasSameDegree(m). -1 if there
		// isn't
		int index = -1;
		for (int i = 0; i < mArray.length; i++) {
			if (mArray[i] != null && mArray[i].hasSameDegrees(m)) {
				index = i;
			}
		}
		return index;
	}

	public void joinCoefficients(Monomial[] mArray, Monomial m, int index) {
		// input: monomial array, monomial and an index
		// output: change marray[index] so that the coefficient will be the sum
		// of m's coefficient and marray[index]'s coefficient
		mArray[index].setCoefficient(m.getCoefficient() + mArray[index].getCoefficient());
	}

	public void addToArray(Monomial[] mArray, Monomial m) {
		// input: monom array and monom
		// output: adds the monom to the first empty space in the array
		int i = 0;
		while (mArray[i] != null) {// might do an error
			i++;
		}
		mArray[i] = m.getCopy();
	}

	/**
	 * @pre other != null
	 * @post Creates a new Polynomial which is the product of this polynomial
	 *       and other. E.g., the product of 13b^2x^3z+15 and -4b^2x^3z is
	 *       -52b^4x^6z^2-60b^2x^3z
	 */
	public Polynomial multiply(Polynomial other) {
		int m1Count = this.getMonomialCount();
		int m2Count = other.getMonomialCount();
		Monomial[] m = new Monomial[m1Count * m2Count];
		int counter = 0;
		for (int i = 0; i < m1Count; i++) {
			for (int j = 0; j < m2Count; j++) {
				Monomial m1 = this.getMonomial(i);
				Monomial m2 = other.getMonomial(j);
				m[counter] = multiplicationJoin(m1, m2);
				counter++;
			}
		}

		for (int i = 0; i < m.length; i++) {
			for (int j = i + 1; j < m.length; j++) {
				if (m[i] != null && m[j] != null && m[i].hasSameDegrees(m[j])) {
					m[i] = multiplicationJoin(m[i], m[j]);
					m[j] = null;
				}
			}
		}

		counter = 0;
		for (int i = 0; i < m.length; i++) {
			if (m[i] != null) {
				counter++;
			}
		}

		Monomial[] newM = new Monomial[counter];
		counter = 0;
		for (int i = 0; i < m.length; i++) {
			if (m[i] != null) {
				newM[counter] = m[i];
				counter++;
			}
		}

		Polynomial p = new Polynomial(newM);
		return p;
	}

	public Monomial multiplicationJoin(Monomial m1, Monomial m2) {
		// input: two monoms
		// output: multiplication join them
		Monomial m3 = new Monomial(m1.getCoefficient() * m2.getCoefficient());
		for (int i = 0; i < 26; i++) {
			m3.setDegree((char) (i + 'a'), m1.getDegree((char) (i + 'a')) + m2.getDegree((char) (i + 'a')));
		}
		return m3;
	}

	/**
	 * @pre assignment != null
	 * @pre assignment.length == 26
	 * @return the result of assigning assignment[0] to a, assignment[1] to b
	 *         etc., and computing the value of this Polynomial
	 */
	public int evaluate(int[] assignment) {
		int evaluation = 0;
		for (int i = 0; i < this.monomials.length; i++) {
			evaluation = evaluation + this.monomials[i].evaluate(assignment);
		}
		return evaluation;
	}

	/**
	 * Returns a string representation of this polynomial by the mathematical
	 * convention, but without performing normalization (summing of monomials).
	 * I.e., each monomial is printed according to Monomial.toString(), for
	 * example 13b^2x^3z+15-4b^2x^3z
	 */
	public String toString() {
		String str;
		if (this.monomials.length == 0) {
			str = "0";
		} else {
			str = this.monomials[0].toString();
			for (int i = 1; i < this.monomials.length; i++) {
				if (this.monomials[i].getCoefficient() > 0) {
					str = str + "+" + this.monomials[i].toString();
				} else {
					str = str + this.monomials[i].toString();
				}
			}
		}
		return str;
	}
}
