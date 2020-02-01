package com.springboot.api.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 
 * @author anilt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquibaseDataSelesApiError {

    @Enumerated(EnumType.STRING)
    private final EquibaseDataSelesApiErrorCodes code;

    private final Integer id;

    private final String description;

    private final String detail;
    
    public EquibaseDataSelesApiError(EquibaseDataSelesApiErrorCodes code, Integer id, String description,
            String detail) {
        this.code = code;
        this.id = id;
        this.description = description;
        this.detail = detail;
    }

    public EquibaseDataSelesApiErrorCodes getCode() {
        return code;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDetail() {
        return detail;
    }
    
}
