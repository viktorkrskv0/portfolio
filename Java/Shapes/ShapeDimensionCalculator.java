package il.ac.tau.cs.sw1.shapes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.text.*;

public class ShapeDimensionCalculator {

	public static void main(String[] args) throws FileNotFoundException {

		Shape[] shapes = getShapesFromUser();
		writeShapesToFile("shapes_output.txt", shapes);
	}

	public static Shape[] getShapesFromUser() {
		Scanner user = new Scanner(System.in);
		Shape[] shapes = new Shape[20];
		int shapesPlaceholder = 0;// holds the next place that is empty

		System.out.print("Shape Dimension Calculator \n\n");
		String input = "";

		while (!input.equals("X") && shapesPlaceholder < 20) {
			printMenu();
			input = user.next();
			if (input.equals("E")) {
				int x, y, semiMajorAxis, semiMinorAxis;
				System.out.print("Please enter X coordinate: ");
				x = user.nextInt();
				System.out.print("Please enter Y coordinate: ");
				y = user.nextInt();
				System.out.print("Please enter semi-major axis length: ");
				semiMajorAxis = user.nextInt();
				System.out.print("Please enter semi-minor axis length: ");
				semiMinorAxis = user.nextInt();
				shapes[shapesPlaceholder] = new Ellipse(x, y, semiMajorAxis, semiMinorAxis);
				System.out.println("Shape added: [" + shapes[shapesPlaceholder].getDetails() + "]");
				shapesPlaceholder++;
			} else if (input.equals("R")) {
				int x, y, width, height;
				System.out.print("Please enter X coordinate: ");
				x = user.nextInt();
				System.out.print("Please enter Y coordinate: ");
				y = user.nextInt();
				System.out.print("Please enter width: ");
				width = user.nextInt();
				System.out.print("Please enter height: ");
				height = user.nextInt();
				shapes[shapesPlaceholder] = new Rectangle(x, y, width, height);
				System.out.println("Shape added: [" + shapes[shapesPlaceholder].getDetails() + "]");
				shapesPlaceholder++;
			} else if (input.equals("C")) {
				int x, y, radius;
				System.out.print("Please enter X coordinate: ");
				x = user.nextInt();
				System.out.print("Please enter Y coordinate: ");
				y = user.nextInt();
				System.out.print("Please enter radius: ");
				radius = user.nextInt();
				shapes[shapesPlaceholder] = new Circle(x, y, radius);
				System.out.println("Shape added: [" + shapes[shapesPlaceholder].getDetails() + "]");
				shapesPlaceholder++;
			} else if (input.equals("X")) {
				// do nothing and exit before next iteration
			} else {
				System.out.println("Unknown command. Please try again.");
			}
		}
		user.close();
		return shapes;
	}

	public static void writeShapesToFile(String outputFilename, Shape[] shapes) throws FileNotFoundException {
		PrintWriter file = new PrintWriter(outputFilename);
		file.println("Shape Dimension Calculator");

		int i = 0, circleCounter = 0, rectangleCounter = 0, ellipseCounter = 0;
		float areaSum = 0, perimeterSum = 0;
		String areaHolder;
		String perimeterHolder;

		DecimalFormat decFor = new DecimalFormat("0.00");

		while (i < 20 && shapes[i] != null) {
			file.println(shapes[i].getDetails());
			areaHolder = decFor.format(shapes[i].getArea());
			perimeterHolder = decFor.format(shapes[i].getPerimeter());
			file.println("Area: " + areaHolder + ", Perimeter: " + perimeterHolder);
			areaSum = areaSum + shapes[i].getArea();
			perimeterSum = perimeterSum + shapes[i].getPerimeter();
			if (shapes[i] instanceof Circle) {
				circleCounter++;
			} else if (shapes[i] instanceof Rectangle) {
				rectangleCounter++;
			} else {
				ellipseCounter++;
			}
			i++;
		}

		file.println("Total number of shapes: " + i);
		file.println("Number of Circles: " + circleCounter);
		file.println("Number of Ellipses: " + ellipseCounter);
		file.println("Number of Rectangles: " + rectangleCounter);
		areaHolder = decFor.format(areaSum);
		perimeterHolder = decFor.format(perimeterSum);
		file.println("Total Area sum: " + areaHolder);
		file.println("Total Perimeter sum: " + perimeterHolder);
		file.flush();
		file.close();
	}

	private static void printMenu() {
		// prints the shape menu
		System.out.print("Please choose shape type:\n");
		System.out.print("E – Ellipse\n");
		System.out.print("R – Rectangle\n");
		System.out.print("C – Circle\n");
		System.out.print("X - Exit\n");
	}

}
