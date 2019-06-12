package hw2;

public class Assignment02Q01Sec01 {

	public static void main(String[] args) {
		//input: some chars
		//output: char that their ascii number is even
		for (int i = 0; i < args.length; i++) {
			char c = args[i].charAt(0);
			int ascii = c;
			if (ascii % 2 == 0) {
				System.out.println(c);
			}

		}

	}
}
