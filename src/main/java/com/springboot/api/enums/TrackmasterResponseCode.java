package com.springboot.api.enums;

/**
 * @author anilt
 */
public enum TrackmasterResponseCode {
	
    NO_CONTENT("The request was successful; the resource was deleted. The deleted resource representation will not be returned"),
    NOT_FOUND("We could not locate the resource based on the specified URI"),
    METHOD_NOT_ALLOWED("DELETE is not supported for the resource"),
    TOO_MANY_REQUESTS("Your application is sending too many simultaneous requests"),
    SERVER_ERROR("We could not create or update the resource. Please try again"),
    SERVICE_UNAVAILABLE("We are temporarily unable to service the request. Please wait for a bit and try again.");

    private String description;

    private TrackmasterResponseCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
