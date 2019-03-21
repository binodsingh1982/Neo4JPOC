package com.stufusion.nlp.textvalidator;

/**
 * @author Sunil
 *
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String exceptionCode;
    private String exceptionMessage;
    private Integer responseCode;

    /**
     * @param exceptionCode
     */
    public BusinessException(String exceptionCode) {
        super(exceptionCode);
        this.setExceptionCode(exceptionCode);
    }

    /**
     * @param exceptionCode
     */
    public BusinessException(String exceptionCode, Integer respCode) {
        super(exceptionCode);
        this.setExceptionCode(exceptionCode);
        this.responseCode = respCode;
    }

    /**
     * @param s
     * @param throwable
     */
    public BusinessException(String s, Throwable throwable) {
        super(s, throwable);
        this.exceptionCode = s;
    }

    /**
     * @param throwable
     */
    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    /**
     * @param exceptionCode
     */
    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * @return
     */
    public String getExceptionCode() {
        return exceptionCode;
    }

    /**
     * @return
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * @param exceptionMessage
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * @return the responseCode
     */
    public Integer getResponseCode() {
        return responseCode;
    }
}
