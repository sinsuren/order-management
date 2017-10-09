package com.sinsuren.order.management.core.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Created by surender.s on 06/10/17.
 */
@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrderManagementException extends Exception{
    private int errorStatusCode;
    private int errorInternalStatusCode;
    private String errorReasonCode;
    private String errorDescription;
    private HttpStatus status;
    private Map<String, String> additionalInfo;


    public OrderManagementException(Errors errors) {
        super(errors.getDescription());
        this.errorStatusCode = errors.getStatus().value();
        this.errorDescription = errors.getDescription();
        this.errorReasonCode = errors.getCode();
        this.status = errors.getStatus();
        this.errorInternalStatusCode = errors.getInternalErrorCode();
    }
}
