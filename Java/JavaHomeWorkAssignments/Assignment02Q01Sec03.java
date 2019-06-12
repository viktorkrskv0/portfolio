package hw2;

public class Assignment02Q01Sec03 {
	public static void main(String args[]) {
		// input: integers
		// output: number of integers that their %3 is even
		int counter = 0;
		for (String number : args) {
			int n = Integer.parseInt(number);
			if ((n % 3) % 2 == 0) {
				counter++;
			}
		}
		System.out.println(counter);

	}

}
