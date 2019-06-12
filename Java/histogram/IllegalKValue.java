package il.ac.tau.cs.sw1.ex8.histogram;

public class IllegalKValue extends Exception {
	public IllegalKValue(int k){
		super("Illegal k value: " + k);
	}
}
