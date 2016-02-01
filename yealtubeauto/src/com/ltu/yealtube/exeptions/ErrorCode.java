package com.ltu.yealtube.exeptions;

// TODO: Auto-generated Javadoc
/**
 * The Enum ErrorCode.
 * @author Uy Phu
 */
public enum ErrorCode {
	
	/** The bad request exception. */
	BAD_REQUEST_EXCEPTION(400, "Bad request"), 
	
	/** The unauthorized exception. */
	UNAUTHORIZED_EXCEPTION(401, "Unauthorized"), 
	
	/** The forbidden exception. */
	FORBIDDEN_EXCEPTION(403, "Forbidden"),
	
	/** The not found exception. */
	NOT_FOUND_EXCEPTION(404, "Not found"),
	
	/** The conflict exception. */
	CONFLICT_EXCEPTION(409, "Conflict"),
	
	/** The internal server error exception. */
	INTERNAL_SERVER_ERROR_EXCEPTION(500, "Internal server error"),
	
	/** The service unavailable exception. */
	SERVICE_UNAVAILABLE_EXCEPTION(503, "Service unvailable"),
	
	/** The system exception. */
	SYSTEM_EXCEPTION(410, "General exception");

	/** The id. */
	private final int id;
	
	/** The msg. */
	private final String msg;

	/**
	 * Instantiates a new error code.
	 *
	 * @param id the id
	 * @param msg the msg
	 */
	ErrorCode(int id, String msg) {
		this.id = id;
		this.msg = msg;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return this.msg;
	}

}
