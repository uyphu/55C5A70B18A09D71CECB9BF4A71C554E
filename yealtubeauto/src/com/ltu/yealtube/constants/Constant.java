package com.ltu.yealtube.constants;

/**
 * The Class Constant.
 * @author uyphu
 */
public class Constant {

	/** The Constant WEB_CLIENT_ID. */
	public static final String WEB_CLIENT_ID = "967485763661-s62auqoraod5k5nhtkp62e8bl9krmcil.apps.googleusercontent.com";
	
	/** The Constant EMAIL_SCOPE. */
	public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
	
	/** The Constant API_KEY. */
	public static final String API_KEY = "AIzaSyBrrKgaw1iD9ETQIF5CRyXWKrriQZT-yQU";
	
	/** The Constant ANDROID_AUDIENCE. */
	public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;
	 
	/** The Constant STRING_EMPTY. */
    public static final String STRING_EMPTY = "";
    
    /** The Constant STRING_ZERO. */
    public static final String STRING_ZERO = "0";
    
    /** The Constant STRING_EXEPTION_DETAIL. */
    public static final String STRING_EXEPTION_DETAIL = "[EXEPTION_DETAIL]";
    
    /** The Constant ACTIVATED. */
    public static final boolean ACTIVATED = true;
    
    /** The Constant IN_WORK_STATUS. */
    public static final int IN_WORK_STATUS = 0;
    
    /** The Constant PENDING_STATUS. */
    public static final int PENDING_STATUS = 1;
    
    /** The Constant APPROVED_STATUS. */
    public static final int APPROVED_STATUS = 2;
    
    /** The Constant CANCELLED_STATUS. */
    public static final int CANCELLED_STATUS = 3;
    
    /** The Constant UTF_8. */
    public static final String UTF_8 = "UTF-8";
    
    //FIXME need to be configurable
    /** The Constant SECRET_KEY. */
    public static final String SECRET_KEY = "myXAuthSecret";
    
    //FIXME need to be configurable
    /** The Constant TOKEN_VALIDITY_IN_SECONDS. */
    public static final int TOKEN_VALIDITY_IN_SECONDS = 1800000;
    
    /** The Constant GOOGLE_USER. */
    public static final String GOOGLE_USER = "G";
    
    /** The Constant FACEBOOK_USER. */
    public static final String FACEBOOK_USER = "F";
    
    /** The Constant SYSTEM_USER. */
    public static final String SYSTEM_USER = "U";
    
    //FIXME need to be configurable
    /** The Constant BASE_URL. */
    public static final String BASE_URL = "https://yealtubetest.appspot.com/admin1";
    
    /** The Constant ADMIN_EMAIL. */
    public static final String ADMIN_EMAIL = "uyphu@yahoo.com";
    
    /** The Constant X_AUTH_TOKEN. */
    public static final String X_AUTH_TOKEN = "x-auth-token";
    
	/** The Constant X_TYPE_TOKEN. */
	public static final String X_TYPE_TOKEN = "x-type-token";
	
	/** The Constant EMPTY_STRING. */
	public static final String EMPTY_STRING = "";
	
	/** The Constant DATE_FORMAT. */
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	/** The Constant LONG_DATE_FORMAT. */
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	/** The Constant MAX_VIEW. */
	public static final int MAX_VIEW = 1100000;
	
	/** The Constant MAX_RECORDS. */
	public static final int MAX_RECORDS = 10;
	
	/** The Constant MAX_DAYS. */
	public static final int MAX_DAYS = 1;
	
	/** The Constant MAX_AVERAGE. */
	public static final int MAX_AVERAGE = 20;
	
	public static final int MAX_PAGE_COMMENT = 10; //Each page has 20 comments
	
	public static final int MAX_HOUR = 24;
	
	public static final int MAX_MINUTE = 10;
	
	public static final Long ADMIN_ID = 1L;
}
