package com.springboot.api.to;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 * @author anilt
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquibaseDataSelesApiErrorResource extends ResourceSupport {
	
	    @JsonProperty("errors")
	    public final List<EquibaseDataSelesApiError> errors;

	    @JsonCreator
	    public EquibaseDataSelesApiErrorResource(@JsonProperty("errors") List<EquibaseDataSelesApiError> trackmasterErrors) {
	        this.errors = trackmasterErrors;
	    }
	
}
