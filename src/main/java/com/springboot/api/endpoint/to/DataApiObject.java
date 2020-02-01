package com.springboot.api.endpoint.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface DataApiObject extends Serializable {

	
	
}
