package com.sinsuren.order.management.util;

import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

/**
 * Created by surender.s on 06/10/17.
 */
public class TransactionFilter implements Filter {
    private static final String TXN_ID = "HTTP_X_TRANSACTION_ID";
    public static final String USE_CASE_VALUE = "ORDER_MANAGEMENT";
    public static final String TENANT_ID = "ORDER_MANAGEMENT";
    public static final String TXN_ID_MDC = "txnId";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            MDC.put(TXN_ID_MDC, createTxnId(httpServletRequest, httpServletResponse));
            filterChain.doFilter(request, response);
        } finally {
            ClientHeaders.removeAll();
            MDC.remove(TXN_ID_MDC);
        }
    }

    @Override
    public void destroy() {

    }

    private String createTxnId(HttpServletResponse httpServletResponse) {
        String txnId = "TXN-" + UUID.randomUUID().toString();
        httpServletResponse.addHeader(Headers.TXN_ID.getHeader(), txnId);
        ClientHeaders.put(Headers.TXN_ID.getHeader(), txnId);
        return txnId;
    }

    private String createTxnId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String txnId = httpServletRequest.getHeader(TXN_ID) == null ? createTxnId(httpServletResponse)
                : httpServletRequest.getHeader(TXN_ID);
        addConstantHeader();
        setResponse(httpServletRequest, httpServletResponse);

        return txnId;
    }

    public static void addConstantHeader() {
        ClientHeaders.put(Headers.USE_CASE.getHeader(), USE_CASE_VALUE);
        ClientHeaders.put(Headers.TENANT_ID.getHeader(), TENANT_ID);
    }

    private void setResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<String> allHeader = Headers.getAllHeader();
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            if (allHeader.contains(header)) {
                httpServletResponse.addHeader(header, httpServletRequest.getHeader(header));
                ClientHeaders.put(header, httpServletRequest.getHeader(header));
            }
        }
    }


}
