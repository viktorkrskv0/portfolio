package hw2;

public class Assignment02Q03 {
	public static void main(String args[]) {
		// input: integers
		// output: the number of integer each digit from 0 to 9 is shown in
		int[] counter = new int[10];

		for (int i = 0; i < 10; i++) {
			for (String number : args) {
				String n = Integer.toString(i);
				if (number.indexOf(n) != -1) {
					counter[i]++;
				}
			}
		}
		for(String number: args){
			for(int i= 0;i<number.length();i++){
				int n = Integer.parseInt(Character.toString(number.charAt(i)));
				System.out.print(n + ":" + counter[n] + " ");
			}
		}
					
		

	}
}
