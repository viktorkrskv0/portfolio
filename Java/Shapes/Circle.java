package il.ac.tau.cs.sw1.shapes;

public class Circle implements Shape {

	private int x;
	private int y;
	private int radius;

	// x and y are the coordinates of the canter
	public Circle(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public float getArea() {
		return (float)(Math.PI * this.radius * this.radius);
	}

	@Override
	public float getPerimeter() {
		return (float)(2 * Math.PI * this.radius);
	}

	@Override
	public String getDetails() {
		return "Circle: X=" + this.x + ", Y=" + this.y + ", Radius=" + this.radius;
	}

}
