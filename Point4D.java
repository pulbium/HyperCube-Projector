package pl.edu.pw.fizyka.pojava.HyperCube;

import java.awt.Point;
import javax.swing.JLabel;

public class Point4D extends Point{
	private static final long serialVersionUID = 1L;
	private	double x,y,z,w;
	JLabel coords = new JLabel();
	
	public Point4D() {
		this.x=0;
		this.y=0;
		this.z=0;
		this.w=0;
	}
	
	public Point4D(double x, double y) {
		this.x=x;
		this.y=y;
	}
	
	public Point4D(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Point4D(double x, double y, double z, double w) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
	}	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public double getW() {
		return w;
	}
	double[] toVec(){
		double[] m = new double[4];
			m[0] = x;
			m[1] = y;
			m[2] = z;
			m[3] = w;
		
		return m;
	}
}

