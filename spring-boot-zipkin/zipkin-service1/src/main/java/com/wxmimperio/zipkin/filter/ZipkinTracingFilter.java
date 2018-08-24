package com.wxmimperio.zipkin.filter;

import brave.http.HttpTracing;
import org.springframework.beans.factory.annotation.Autowired;
import brave.servlet.TracingFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/*", filterName = "zipkinTracingFilter")
public class ZipkinTracingFilter implements Filter {

    @Autowired
    private HttpTracing httpTracing;

    private Filter delegate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        delegate = TracingFilter.create(httpTracing);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Filter tracingFilter = delegate;
        if (tracingFilter == null) { // don't break on initialization error.
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            tracingFilter.doFilter(servletRequest, servletResponse, filterChain);
        }
    }

    @Override
    public void destroy() {

    }
}
