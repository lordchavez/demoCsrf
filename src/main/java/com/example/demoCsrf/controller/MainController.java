package com.example.demoCsrf.controller;

import com.example.demoCsrf.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Log4j2
@Controller
public class MainController {

    @GetMapping( { "/", "/login" } )
    public ModelAndView menu() {
        log.info( "MainController.login" );

        ModelAndView modelAndView = new ModelAndView( "login" );
        return modelAndView;
    }

    @RequestMapping( value = "/menu", method = RequestMethod.POST )
    public ModelAndView menu(  HttpServletRequest httpRequest,  CsrfToken token ) {
        log.info( "MainController.menu" );

        HttpSessionCsrfTokenRepository session = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = session.loadToken(httpRequest);
        if ( csrfToken != null ) {
            log.info("csrfToken=" + csrfToken.getToken());
        }

        User user = new User();
        user.setName( httpRequest.getParameter("username") );

        ModelAndView modelAndView = new ModelAndView( "menu" );
        modelAndView.addObject( "user", user );
        return modelAndView;
    }

    @RequestMapping( value="/logout", method=RequestMethod.POST )
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        log.info( "MainController.logout" );
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        ModelAndView modelAndView = new ModelAndView( "login" );
        return modelAndView;
    }

}
