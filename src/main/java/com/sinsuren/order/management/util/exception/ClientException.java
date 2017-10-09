package com.sinsuren.order.management.util.exception;

import com.sinsuren.order.management.core.dto.ClientErrorResponse;
import com.sinsuren.order.management.util.TransactionFilter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by surender.s on 06/10/17.
 */
@Slf4j
public abstract class ClientException extends RuntimeException {
    private static final long serialVersionUID = 2405997615249923954L;

    @Getter
    private ResponseEntity responseEntity;

    public ClientException(String message, ResponseEntity responseEntity) {
        super(message);
        this.responseEntity = responseEntity;
        log.error(message);
    }

    public ClientException(String message, Exception exception) {
        super(String.format("%s %s", message, exception.getMessage()));
        String errorDescription = message + exception.getLocalizedMessage();
        ClientErrorResponse errorResponse = getClientErrorResponse(errorDescription);
    }


    public ClientException(String msg) {
        super(msg);
        ClientErrorResponse errorResponse = getClientErrorResponse(msg);
        this.responseEntity = ResponseEntity.status(getStatus()).body(errorResponse);
        log.error(msg);
    }


    private ClientErrorResponse getClientErrorResponse(String errorDescription) {
        return ClientErrorResponse.builder()
                .errorStatusCode(getStatus().value())
                .txnId(MDC.get(TransactionFilter.TXN_ID_MDC))
                .errorReasonCode(this.getClass().getSimpleName())
                .errorInternalStatusCode(getErrorCode(this.getClass().getSimpleName()))
                .errorDescription(errorDescription)
                .build();

    }

    public static int getErrorCode(String message) {
        return Math.abs(message.hashCode()%1000);
    }
    protected abstract HttpStatus getStatus();
}
