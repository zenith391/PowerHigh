package org.lggl.utils;

public class LGGLException extends Exception {
	private static final long serialVersionUID = 6280330730174014901L;
	public LGGLException(final Throwable cause) {
        super(cause);
    }
    public LGGLException() {

    }
    public LGGLException(final String message, final Throwable cause) {
        super(message, cause);
    }
    public LGGLException(final String message) {
        super(message);
    }
}
