package com.stufusion.nlp.textvalidator;

/**
 * @author vverma
 *
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param s
     */
    public SystemException(String s) {
        super(s);
    }

    /**
     * @param s
     * @param throwable
     */
    public SystemException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * @param throwable
     */
    public SystemException(Throwable throwable) {
        super(throwable);
    }
}
