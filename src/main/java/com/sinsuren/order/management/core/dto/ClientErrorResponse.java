package com.sinsuren.order.management.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinsuren.order.management.core.exceptions.Errors;
import com.sinsuren.order.management.core.exceptions.OrderManagementException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * Created by surender.s on 06/10/17.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientErrorResponse {

    private int errorStatusCode;
    private int errorInternalStatusCode;
    private String errorReasonCode;
    private String txnId;
    private String errorDescription;
    private Map<String, String> additionalInfo;


    public ClientErrorResponse(OrderManagementException orderManagementException) {
        this.errorStatusCode = orderManagementException.getErrorStatusCode();
        this.errorReasonCode = orderManagementException.getErrorReasonCode();
        this.errorDescription = orderManagementException.getErrorDescription();

        this.errorInternalStatusCode = orderManagementException.getErrorInternalStatusCode();
        this.additionalInfo = orderManagementException.getAdditionalInfo();
    }

    public ClientErrorResponse(Errors errors) {
        this.errorStatusCode = errors.getStatus().value();
        this.errorReasonCode = errors.getCode();
        this.errorDescription = errors.getDescription();
        this.errorInternalStatusCode = errors.getInternalErrorCode();
    }
}
