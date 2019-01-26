package com.github.delve.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

class LoggableDispatcherServlet extends DispatcherServlet {

    private final Log logger = LogFactory.getLog(getClass());
    private final String apiBasePath;

    LoggableDispatcherServlet(final WebApplicationContext webApplicationContext, final String apiBasePath) {
        super(webApplicationContext);
        this.apiBasePath = "/" + apiBasePath;
    }

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getRequestURI().startsWith(apiBasePath)) {
            if (!(request instanceof ContentCachingRequestWrapper)) {
                request = new ContentCachingRequestWrapper(request);
            }
            if (!(response instanceof ContentCachingResponseWrapper)) {
                response = new ContentCachingResponseWrapper(response);
            }
            final HandlerExecutionChain handler = getHandler(request);

            try {
                super.doDispatch(request, response);
            } finally {
                log(request, response, handler);
                updateResponse(response);
            }
        } else {
            super.doDispatch(request, response);
        }
    }

    private void log(final HttpServletRequest requestToCache, final HttpServletResponse responseToCache, final HandlerExecutionChain handler) {
        final LogMessage log = new LogMessage();
        log.setHttpStatus(responseToCache.getStatus());
        log.setHttpMethod(requestToCache.getMethod());
        log.setPath(requestToCache.getRequestURI());
        log.setClientIp(requestToCache.getRemoteAddr());
        log.setJavaMethod(handler.toString());
        log.setResponse(getResponsePayload(responseToCache));

        logger.info(log);
    }

    private String getResponsePayload(final HttpServletResponse response) {
        final ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {

            final byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                final int length = Math.min(buf.length, 5120);
                try {
                    return new String(buf, 0, length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    // NOOP
                }
            }
        }
        return "[unknown]";
    }

    private void updateResponse(final HttpServletResponse response) throws IOException {
        final ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }

    private class LogMessage {

        private int httpStatus;
        private String responsePayload;
        private String javaMethod;
        private String clientIp;
        private String path;
        private String method;

        private void setHttpStatus(final int httpStatus) {
            this.httpStatus = httpStatus;
        }

        private void setResponse(final String responsePayload) {
            this.responsePayload = responsePayload;
        }

        private void setJavaMethod(final String javaMethod) {
            this.javaMethod = javaMethod;
        }

        private void setClientIp(final String clientIp) {
            this.clientIp = clientIp;
        }

        private void setPath(final String path) {
            this.path = path;
        }

        private void setHttpMethod(final String method) {
            this.method = method;
        }

        @Override
        public String toString() {
            return "{" +
                    "httpStatus=" + httpStatus +
                    ", responsePayload='" + responsePayload + '\'' +
                    ", javaMethod='" + javaMethod + '\'' +
                    ", clientIp='" + clientIp + '\'' +
                    ", path='" + path + '\'' +
                    ", method='" + method + '\'' +
                    '}';
        }
    }
}
