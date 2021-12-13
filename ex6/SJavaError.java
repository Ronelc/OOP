package oop.ex6.main;

/**
 * class that creates an object of error.
 */
public class SJavaError extends Exception{

	private static final long serialVersionUID = 1L;

	public SJavaError(String msg){
		super(msg);
	}
}