package com.ltu.yealtube.exeptions;

import com.google.api.server.spi.ServiceException;

/**
 * The Class ProconcoException.
 */
public class CommonException extends ServiceException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3367849469456953730L;

	/**
	 * Instantiates a new proconco exception.
	 *
	 * @param statusCode the status code
	 * @param statusMessage the status message
	 */
	public CommonException(int statusCode, String statusMessage) {
		super(statusCode, statusMessage);
	}
	
	/**
	 * Instantiates a new proconco exception.
	 *
	 * @param code the code
	 * @param codeDetail the code detail
	 */
	public CommonException(ErrorCode code, ErrorCodeDetail codeDetail) {
		super(code.getId(), codeDetail.getMsg());
	}
	
	/**
	 * Instantiates a new proconco exception.
	 *
	 * @param code the code
	 * @param statusMessage the status message
	 */
	public CommonException(ErrorCode code, String statusMessage) {
		super(code.getId(), statusMessage);
	}

	/**
	 * Instantiates a new proconco exception.
	 *
	 * @param statusCode the status code
	 * @param statusMessage the status message
	 * @param cause the cause
	 */
	public CommonException(int statusCode, String statusMessage, Throwable cause) {
		super(statusCode, statusMessage, cause);
	}

	/**
	 * Instantiates a new proconco exception.
	 *
	 * @param statusCode the status code
	 * @param cause the cause
	 */
	public CommonException(int statusCode, Throwable cause) {
		super(statusCode, cause);
	}

}
