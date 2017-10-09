package com.sinsuren.order.management.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surender.s on 06/10/17.
 */
public enum Headers {
    TXN_ID ( "HTTP_X_TRANSACTION_ID"),
    SESSION_KEY ( "AUTHN_SECURITY_CONTEXT"),
    USER_ID ( "X-User-Id"),
    TENANT_ID ( "X_TENANT_ID"),
    USE_CASE ( "X_USE_CASE"),
    REQUEST_ID ( "X_REQUEST_ID"),
    EVENT_NAME ( "X_EVENT_NAME");

    private final String  X_HEADER;

    Headers(String header) {
        X_HEADER = header;
    }

    public String getHeader() {
        return X_HEADER;
    }

    public static List<String> getAllHeader() {
        List<String> headers = new ArrayList<>();
        for (Headers header : Headers.values()) {
            headers.add(header.getHeader());
        }
        return headers;
    }
}
