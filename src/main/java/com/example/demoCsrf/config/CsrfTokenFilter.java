package com.example.demoCsrf.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class CsrfTokenFilter implements Filter {

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
        log.info( "CsrfTokenFilter.doFilter" );

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        HttpSessionCsrfTokenRepository session = new HttpSessionCsrfTokenRepository();
        CsrfToken token = session.loadToken( httpRequest );

        if ( token == null && requestURI.contains( "login" ) ) {
            token = session.generateToken( httpRequest );
            log.info( "Token Generated={}", token.getToken() );
        }

        session.saveToken( token, httpRequest, httpResponse );

        chain.doFilter( httpRequest, httpResponse );
    }
}
