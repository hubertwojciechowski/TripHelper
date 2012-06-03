package tripHelper.utils;

import java.util.ArrayList;

public class Path {

	/*
	 * Kolejne kroki sciezki
	 */
	public ArrayList<Segment> steps;
	
	public Path(){
		this.steps = new ArrayList<Segment>();
	}
	
	public void add(Segment s) {
		this.steps.add(s);
	}
	
	public void add(String from, String to, Float cost,  Segment.type type) {
		
		this.steps.add(new Segment(from,to,cost,type));
		
		
	}
	
	
	
	
}
