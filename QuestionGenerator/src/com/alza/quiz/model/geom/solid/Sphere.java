package com.alza.quiz.model.geom.solid;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.alza.quiz.model.geom.Path;
import com.alza.quiz.model.geom.Point3D;
import com.alza.quiz.model.geom.Shapes2D;
import com.alza.quiz.model.geom.Shapes3D;
import com.alza.quiz.model.geom.plane.Geom;

public class Sphere implements Shapes3D{
	private double radius;
	private boolean showRadiusValue=true;
	public Sphere() {
		
	}
	public Sphere(double radius) {
		super();
		this.radius = radius;
	}
	
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	@Override
	public double getSurfaceArea() {
		return Geom.PI * radius * radius * 4;
	}
	@Override
	
	public double getVolume() {
		return getSurfaceArea() * radius /3;
	}

	public boolean isShowRadiusValue() {
		return showRadiusValue;
	}
	public void setShowRadiusValue(boolean showRadiusValue) {
		this.showRadiusValue = showRadiusValue;
	}
	@Override
	public List<Shapes2D> getFaces() {
		return null;
	}
	@Override
	public String getName() {
		return "sphere";
	}

	@Override
	public Shapes3D createExample() {
		double radius = ThreadLocalRandom.current().nextInt(5, 26);
		return new Sphere(radius);
	}

	@Override
	public List<Point3D> getVertices() {
		List<Point3D> points = new ArrayList<Point3D>();
		Point3D a,b,c,d;
		
		a = new Point3D(0, radius, 0); //left
		b = new Point3D(radius*2, radius , 0); //right
		c = new Point3D(radius, 0 , 0); // top
		d = new Point3D(radius, 2 *radius, 0);//bottom
		points.add(a);points.add(b);points.add(c);points.add(d);
		return points;
	}
	@Override
	public int getEdgeCount() {
		return 8;
	}
	@Override
	public List<Path> getPaths() {
		
		List<Path> l = new ArrayList<Path>();
		
		
		Point3D center = new Point3D(radius,radius,0);
		
		l.add(Path.createLinePathDotted(center, getVertices().get(1)));
		
		l.add(Path.createArcPath(center, radius, 
				radius, 0, 360)); // full circle front projection
		l.add(Path.createArcPath(center, radius, 
				radius/2, 0, -180)); // oval projection of horizontal cut, front side
		l.add(Path.createArcPathDashed(center, radius, 
				radius/2, 0, 180)); // oval projection of horizontal cut, backside
		
		if (showRadiusValue) {
			l.add(Path.createTextPath(Geom.formatMeasurement(radius), 
					Point3D.getMidPoint(getVertices().get(1), center)
					, Path.SHIFT_UP,Path.SHIFT_NONE));
		}
		
		return l;
	}
}
