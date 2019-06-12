package hw1;

public class AverageCalculator {
	public static void main(String[] args){
		//input: three integers
		//output: their sum and their avrage
		
		int n1 = Integer.parseInt(args[0]);
		int n2 = Integer.parseInt(args[1]);
		int n3 = Integer.parseInt(args[2]);
		
		int sum = n1+n2+n3;
		double avg = (n1+n2+n3)/3.0; 
		
		System.out.println("The sum is: "+sum+".");
		System.out.println("The average is: "+avg+".");
		
	}
}
