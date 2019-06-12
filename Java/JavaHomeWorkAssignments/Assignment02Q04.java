package hw2;

import java.util.Arrays;

public class Assignment02Q04 {

	public static void main(String args[]) {
		//input: words
		//output: arrays with length and content as specified in Q04
		String[][] table = new String[args.length][];

		for (int i = 0; i < table.length; i++) {
			table[i] = new String[args[i].length()];
			for (int j = 0; j < table[i].length; j++) {
				table[i][j] = args[i];
			}
		}

		for (int i = 0; i < table.length; i++) {
			System.out.println(Arrays.toString(table[i]));
		}
	}
}
