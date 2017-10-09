package com.sinsuren.order.management.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by surender.s on 06/10/17.
 */
@AllArgsConstructor
public enum Errors {
    DUPLICATE_ENTRY("DUPLICATE_ENTRY",1409,HttpStatus.CONFLICT,"Duplicate Entry"),
    BAD_REQUEST("BAD_REQUEST",1400,HttpStatus.BAD_REQUEST,"Bad request body"),

    //5xx
    INTERNAL_ERROR("INTERNAL_ERROR",2500,
                                HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");


    @Getter
    private String code;
    @Getter
    private int internalErrorCode;
    @Getter
    private HttpStatus status;
    @Getter
    private String description;
}
