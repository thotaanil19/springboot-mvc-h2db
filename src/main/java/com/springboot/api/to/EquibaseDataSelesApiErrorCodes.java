package com.springboot.api.to;

/**
 * 
 * @author anilt
 *
 */
public enum EquibaseDataSelesApiErrorCodes {
	
    MISSING_METHOD_ARGUMENTS(4000,"Missing Method Arguments"), 
    
    MISSING_REQUIRED_FIELD_USER_ID(4001,"Missing Required Feild UserId"), 
    INVALID_USER_ID(4002,"Invalid UserId"),
    
    MISSING_REQUIRED_FIELD_PASSWORD(4003,"Missing Required Feild Password in Customer SetUp"),
    PASSWORD_VALIDATIONS_FAILED(4004,"Password does not meet password validations"),
    
    MISSING_REQUIRED_FIELD_NAME(4005,"Missing Required Feild Name"), 
    INVALID_NAME(4006,"Invalid Name"), 
    
    MISSING_REQUIRED_FIELD_LOGIN_ID(4007,"Missing Required Feild LoginId"), 
    INVALID_LOGIN_ID(4008,"Invalid LoginId"), 
    
    MISSING_REQUIRED_FIELD_ACCESS_LEVEL(4009,"Missing Required Feild Access Level"),
    INVALID_ACCESS_LEVEL(4010,"Invalid AccessLevel"),
    MISSING_REQUIRED_FIELD_IS_ACTIVE(4011,"Missing Required Feild IsActive"),
    MISSING_REQUIRED_FIELD_CHANGE_PASSWORD(4012,"Missing Required Feild Change Password"),
    PASSWORDS_NOT_MATCHED(4013,"Passwords do not matched"),
    MISSING_REQUIRED_FIELD_CONFIRM_PASSWORD(4014,"Missing Required Feild Confirm Password "),
    MISSING_REQUIRED_FEILD_PRODUCT_LABEL(4015,"Missing Required Feild Product Label"),
    INVALID_PRODUCT_LABEL(4016,"Invalid Product Label"),
    MISSING_REQUIRED_FIELD_TYPE(4017,"Missing Required Feild Type"),
    INVALID_PRODUCT_TYPE(4018,"Invalid Product Type"),
    MISSING_REQUIRED_FIELD_CACHE_TIME(4019,"Missing Required Feild Cache Time"),
    INVALID_CACHE_TIME(4020,"Invaliad Cache Time"),
    MISSING_REQUIRED_FIELD_LEVEL(4021,"Missing Required Feild Level"),
    INVALID_PRODUCT_LEVEL(4022,"Invalid Product Level"),
    MISSING_REQUIRED_FIELD_PRODUCT_ID(4023,"Missing Required Feild Product Id"),
    INVALID_PRODUCT_ID(4024,"Invalid Product Id"),
    
    INVALID_CUSTOMER_API_KEY(4008,"Invalid Api Key"),
    MISSING_SATRT_DATE(4009,"Missing Start Date"),
    MISSING_END_DATE(4009,"Missing End Date"),
    MISSING_API_LIMITS(4010,"Missing Api limits field in Customer Api Limits"),
    INVALID_EMAIL_FORMAT(4011,"Email field is in invalid format"),
    INVALID_CUSTOMER_API_KEY_LENGTH(4012,"Invalid Customer Api Key length - Length should be less than 40 characters"),
    CUSTOMER_ALREADY_EXISTS_WITH_LOGIN_ID(4013,"Customer Login Id is already in use, Choose diffent login Id"),
    MISSING_REQUIRED_FIELD_CUSTOMER_ID(4014,"Missing Required Feild Customer Id"), 
    CUSTOMER_NOT_EXISTS_FOR_CUSTOMER_ID(4015,"No customer found for given customer id"), 
    
    ADMIN_ALREADY_EXISTS_WITH_LOGIN_ID(4016,"Admin Login Id is already in use, Choose diffent login Id"),
    
    INVALID_API_LIMIT_SATRT_DATE(4017,"Invalid Start Date Format in Customer Api Limits"),
    INVALID_API_LIMIT_END_DATE(4018,"Missing End Date Format in Customer Api Limits"),
    
    MISSING_REQUIRED_COMPANY_NAME(4019,"Missing Required Feild Company Name"), 
    
    WHITE_SPACES_IN_LOGIN_ID(4020,"White Spaces in Login Id"),
    
    INVALID_RESET_DAY_OF_MONTH(4021,"Reset Day Of Month should be 1-25 only"), 
    
    MISSING_PAGE_SIZE(4022,"Missing Page Size"),
    MISSING_PAGE_NUMBER(4023,"Missing Page Number"),
    
    MISSING_BASE_ACCESS_LIMIT(4024,"Missing Required Feild Base Access Limit"), 
    
    CUSTOMER_IS_NOT_ACTIVE(4025, "Customer is not active"),
    
    API_THRESHOLD_EXCEEDED(2026,"API threshold exceeded"),
    API_THRESHOLD_NOT_ASSIGNED(2027,"No API Limits are assigned to Customer"),
    INVALID_REQUEST(4028,"Invalid Request"),
    PRODUCT_NOT_ACTIVE_FOR_CUSTOMER(2029, "Product not active for customer"),
    
    INVALID_BASE_ACCESS_LIMIT(2030,"Invalid Base Access Limit"),
    
    MISSING_CUSTOMER_PRODUCTS(2031,"Missing Customer Products"),
    
    PRODUCT_ALREADY_EXISTS_WITH_TYPE_AND_LEVEL(2032, "Product already exists with type and level"),
    
    NO_PRODUCT_FOUND_WITH_TYPE_AND_LEVEL(2033, "No Product found with type and level"),
    UNAUTHORIZED_ACCESS_TO_PRODUCT(2034, "Unauthorized access to this product"),
    INVALID_TRACK(2035, "Invalid Track"),
    UNAUTHORIZED_ACCESS_TO_TRACK(2036, "Unauthorized access to this track"),
    
    INVALID_PLAYER_TYPE(2037, "Invalid player type"),
    
    ;

    private Integer id;
    private String description;

    private EquibaseDataSelesApiErrorCodes(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
