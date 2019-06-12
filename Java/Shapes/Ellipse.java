package il.ac.tau.cs.sw1.shapes;

public class Ellipse implements Shape{

	private int x;
	private int y;
	private int semiMajorAxis;
	private int semiMinorAxis;
	
	// x and y are the coordinates of the ellipse center 
	// semiMajorAxis is the ‘large radius’ of the ellipse  
	// semiMinorAxis is the ‘small radius’ of the ellipse 
	public Ellipse(int x, int y, int semiMajorAxis, int semiMinorAxis){
		this.x = x;
		this.y = y;
		this.semiMajorAxis = semiMajorAxis;
		this.semiMinorAxis = semiMinorAxis;
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


	public int getSemiMajorAxis() {
		return semiMajorAxis;
	}


	public void setSemiMajorAxis(int semiMajorAxis) {
		this.semiMajorAxis = semiMajorAxis;
	}


	public int getSemiMinorAxis() {
		return semiMinorAxis;
	}


	public void setSemiMinorAxis(int semiMinorAxis) {
		this.semiMinorAxis = semiMinorAxis;
	}


	@Override
	public float getArea() {
		return (float)(Math.PI*this.semiMinorAxis*this.semiMajorAxis);
	}

	@Override
	public float getPerimeter() {		
		return (float)(Math.PI*(this.semiMinorAxis+this.semiMajorAxis));
	}

	@Override
	public String getDetails() {
		return "Ellipse: X="+this.x+", Y="+this.y+", SemiMajorAxis="+this.semiMajorAxis+", SemiMinorAxis="+this.semiMinorAxis ;
	}

}
