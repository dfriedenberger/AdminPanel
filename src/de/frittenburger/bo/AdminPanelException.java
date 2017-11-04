package de.frittenburger.bo;

public class AdminPanelException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int TSystem       = 0;
	public static final int TValidation   = 1;
	public static final int TAccessDenied = 2;
	
	
	private int i;
	private String m;

	public AdminPanelException(Exception e) {
		this.i = TSystem;
		this.m = e.getMessage();
	}

	public AdminPanelException(int i, String message) {
		this.i = i;
		this.m = message;
	}

	@Override
	public String getMessage()
	{
		return String.format("%d %s",i,m);
	}

	public int getLevel() {
		return i;
	}

}
