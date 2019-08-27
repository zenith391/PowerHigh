package org.powerhigh.utils;

public class PowerHighException extends Exception {
	
	private static final long serialVersionUID = 6280330730174014901L;
	
	public PowerHighException(final Throwable cause) {
        super(cause);
    }
	
    public PowerHighException() {}
    
    public PowerHighException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public PowerHighException(final String message) {
        super(message);
    }
}