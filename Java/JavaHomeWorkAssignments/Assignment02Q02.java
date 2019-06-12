package hw2;

public class Assignment02Q02 {
	public static void main(String args[]) {
		// input: different numbers for radius
		// output: circle area and circle circumference
		int index = 0;
		for (String radius : args) {
			double r = Double.parseDouble(radius);
			double area = Math.PI * Math.pow(r, 2);
			double circumference = 2 * Math.PI * r;
			System.out.println("For the:" + index + " argument");
			System.out.println("The Circle area is:" + area);
			System.out.println("The Circle circumference:" + circumference);
			index++;
		}

	}
}
