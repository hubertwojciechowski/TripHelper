package tripHelper.utils;

import jade.util.leap.Serializable;

public class Segment implements Serializable {
	
	public String from;
	public String to;
	public float cost;
	public type trasportType;
	
	public static enum type {none,train,bus,plain}; 

	
	public Segment(String from, String to, Float cost, type type){
		this.from = from;
		this.to = to;
		this.cost = cost;
		this.trasportType = type;
	}
	
	
}
