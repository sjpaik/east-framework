package com.east.framework.core.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.east.framework.core.exception.model.BaseErrorInfo;

/**
 * abstract Exception Handler.
 *
 * Updated on : 2015-10-01 Updated by : love.
 */
public abstract class AbstractControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractControllerExceptionHandler.class);

    /**
     * @param messageSourceAccessor
     *            MessageSourceAccessor
     */
    public abstract void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor);

    /**
     * @return MessageSourceAccessor
     */
    public abstract MessageSourceAccessor getMessageSourceAccessor();

    /**
     * 
     * ResponseEntity<BaseErrorInfo> 객체 리턴.
     * 
     * @param headers
     *            HttpHeaders
     * 
     * @param httpStatus
     *            HttpStatus
     * @param code
     *            BasicErrorInfo.code
     * @param detail
     *            BasicErrorInfo.detail
     * 
     * @return ResponseEntity<BasicErrorInfo>
     */
    protected ResponseEntity<BaseErrorInfo> makeResponseEntity(HttpHeaders headers, HttpStatus httpStatus, String code, String detail) {
        String message = "";
        try {
            message = getMessageSourceAccessor().getMessage(code);
        } catch (Exception e) {
            LOGGER.warn("ResponseEntity.Exception : ", e);
            message = code;
        }
        return this.makeResponseEntity(headers, httpStatus, code, message, detail);
    }

    /**
     * 
     * ResponseEntity<BaseErrorInfo> 객체 리턴.
     * 
     * @param headers
     *            HttpHeaders
     * 
     * @param httpStatus
     *            HttpStatus
     * @param code
     *            BasicErrorInfo.code
     * @param args
     *            String[]
     * @param detail
     *            BasicErrorInfo.detail
     * 
     * @return ResponseEntity<BasicErrorInfo>
     */
    protected ResponseEntity<BaseErrorInfo> makeResponseEntity(HttpHeaders headers, HttpStatus httpStatus, String code, String[] args, String detail) {
        String message = "";
        try {
            message = getMessageSourceAccessor().getMessage(code, args);
        } catch (Exception e) {
            LOGGER.warn("ResponseEntity.Exception : ", e);
            message = code;
        }
        return this.makeResponseEntity(headers, httpStatus, code, message, detail);
    }

    /**
     * 
     * ResponseEntity<BaseErrorInfo> 객체 리턴.
     * 
     * @param headers
     *            HttpHeaders
     * @param httpStatus
     *            HttpStatus
     * @param code
     *            BasicErrorInfo.code
     * @param message
     *            BasicErrorInfo.message
     * @param detail
     *            BasicErrorInfo.detail
     * 
     * @return ResponseEntity<BasicErrorInfo>
     */
    protected ResponseEntity<BaseErrorInfo> makeResponseEntity(HttpHeaders headers, HttpStatus httpStatus, String code, String message, String detail) {
        BaseErrorInfo baseErrorInfo = new BaseErrorInfo();
        baseErrorInfo.setCode(code);
        baseErrorInfo.setMessage(message);
        baseErrorInfo.setDetail(detail.toString());

        return new ResponseEntity<BaseErrorInfo>(baseErrorInfo, headers, httpStatus);
    }

}
