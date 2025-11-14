package com.jsp.exception.handler;

import com.jsp.exception.ApplicationException;
import com.jsp.exception.ExceptionConstants;
import com.jsp.exception.response.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationExceptionHandlerTest {

    private ApplicationExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setup() {
        MessageSource messageSource = mock(MessageSource.class);
        handler = new ApplicationExceptionHandler(messageSource);
        request = mock(HttpServletRequest.class);

        when(messageSource.getMessage(eq("failure.status"), any(), any()))
                .thenReturn("FAILED");

        when(messageSource.getMessage(eq("internal.server.error"), any(), any()))
                .thenReturn("Internal error");

        when(messageSource.getMessage(eq("cloud.internal.server.error"), any(), any(), any()))
                .thenReturn("Cloud Internal Error");
    }

    @Test
    void testHandleException_WhenNotDeviceURI_UsingHeader() {
        ApplicationException ex = new ApplicationException("Cloud error");

        when(request.getRequestURI()).thenReturn("/v1/cloud/action");
        when(request.getHeader("X-Correlation-Id")).thenReturn("CID-111");

        ResponseEntity<Object> response = handler.handleException(ex, Locale.ENGLISH, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(ResponseObject.class, response.getBody());

        ResponseObject resp = (ResponseObject) response.getBody();
        assertEquals(ExceptionConstants.ERROR_CODE_APPLICATION, resp.getErrors().getCode());
        assertNull(resp.getErrors().getMessage());
        assertNotNull(resp.getErrors().getTimestamp());
    }

    @Test
    void testHandleException_WhenNotDeviceURI_UsingAttribute() {
        ApplicationException ex = new ApplicationException("Another cloud error");

        when(request.getRequestURI()).thenReturn("/v1/cloud/operation");
        when(request.getHeader("X-Correlation-Id")).thenReturn(null);
        when(request.getAttribute("X-Correlation-Id")).thenReturn("ATTR-999");

        ResponseEntity<Object> response = handler.handleException(ex, Locale.ENGLISH, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(ResponseObject.class, response.getBody());

        ResponseObject resp = (ResponseObject) response.getBody();
        assertEquals(ExceptionConstants.ERROR_CODE_APPLICATION, resp.getErrors().getCode());
        assertNull(resp.getErrors().getMessage());
        assertNotNull(resp.getErrors().getTimestamp());
    }
}

