package com.ltu.yealtube.exeptions;

// TODO: Auto-generated Javadoc
/**
 * The Enum ErrorCodeDetail.
 */
public enum ErrorCodeDetail {

	/** The error duplicated name. */
	ERROR_DUPLICATED_NAME(600, "[600] Name already exists"), 
	
	/** The error duplicated email. */
	ERROR_DUPLICATED_EMAIL(601, "[601] Email already exists"), 
	
	/** The error exist object. */
	ERROR_EXIST_OBJECT(602, "[602] Object already exists"),
	
	/** The error record not found. */
	ERROR_RECORD_NOT_FOUND(603, "[603] Object not found"),
	
	/** The error parse query. */
	ERROR_PARSE_QUERY(604, "[604] Parsing query string error"),
	
	/** The error remove entity. */
	ERROR_REMOVE_ENTITY(605, "[605] Remove entity error"),
	
	/** The error insert entity. */
	ERROR_INSERT_ENTITY(606, "[606] Insert entity error"),
	
	/** The error update entity. */
	ERROR_UPDATE_ENTITY(607, "[607] Update entity error"),
	
	/** The error conflict login. */
	ERROR_CONFLICT_LOGIN(608, "[608] Login already exists"),
	
	/** The error conflict email. */
	ERROR_CONFLICT_EMAIL(609, "[609] Email already exists"),
	
	/** The error conflict login and email. */
	ERROR_CONFLICT_LOGIN_AND_EMAIL(610, "[610] Login and Email already exists"),
	
	/** The error duplicated role. */
	ERROR_DUPLICATED_ROLE(611, "[611] Duplicated role"),
	
	/** The error not found role. */
	ERROR_NOT_FOUND_ROLE(612, "[612] Role not found"),
	
	/** The error not activated. */
	ERROR_NOT_ACTIVATED(613, "[613] User not activated"),
	
	/** The error invalid password. */
	ERROR_INVALID_PASSWORD(614, "[614] Invalid password"),
	
	/** The error user not found. */
	ERROR_USER_NOT_FOUND(615, "[615] User not found"),
	
	/** The error user not authenticated. */
	ERROR_USER_NOT_AUTHENTICATED(616, "[616] User not authenticated"),
	
	/** The error user not valid. */
	ERROR_USER_NOT_VALID(617, "[617] User not valid"),
	
	/** The error input not valid. */
	ERROR_INPUT_NOT_VALID(618, "[618] User not valid");

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
	ErrorCodeDetail(int id, String msg) {
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
