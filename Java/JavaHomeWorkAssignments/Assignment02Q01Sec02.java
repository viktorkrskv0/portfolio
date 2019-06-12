package hw2;

public class Assignment02Q01Sec02 {
	public static void main(String args[]) {
		// input: doubles
		// output: round everyone down, and print the biggest
		double d0 = Double.parseDouble(args[0]);
		double d1;
		int max0 = (int) d0;
		int max1;
		for (String number : args) {
			d1 = Double.parseDouble(number);
			max1 = (int) d1;
			if (max1 > max0) {
				max0 = max1;
			}
		}
		System.out.println(max0);
	}

}
