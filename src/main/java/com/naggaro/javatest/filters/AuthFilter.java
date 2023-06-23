package com.naggaro.javatest.filters;


import com.naggaro.javatest.constants.NaggaroConstants;
import com.naggaro.javatest.constants.BASERoute;
import com.naggaro.javatest.dto.UserSessionDto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 * The all requests are intercepted at this filterand authenticated. If user is not authorized then un-authenticated response
 * is sent back. The authentication and test resources are whitelisted for authentication
 */

@Component
@Order(1)
public class AuthFilter implements Filter {


    public  static HashMap<String, UserSessionDto> loginSessions = new HashMap<>();
    private void init(){

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        init();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;


        if(!req.getRequestURI().contains(BASERoute.BASE_ROUTE+"/auth")) {

                String clientId = req.getHeader("ClientId");
                String accessToken = req.getHeader("Token");
                if( accessToken == null || clientId == null)
                {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, NaggaroConstants.INVALID_TOKEN_OR_EXPIRED);
                    return;
                }

                UserSessionDto userSessionDto = loginSessions.get(clientId);
                if(userSessionDto==null)
                {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, NaggaroConstants.USER_NOT_FOUND);
                    return;
                }

                int diffInMinutes = (int)( (  new Date().getTime() - userSessionDto.getLoginTime().getTime()) / (1000 * 60  ) );


                if(!accessToken.equals(userSessionDto.getToken()) || diffInMinutes > NaggaroConstants.TOKEN_EXPIRY_TIME_IN_MINUTES)
                {
                    loginSessions.remove(clientId);
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, NaggaroConstants.INVALID_TOKEN_OR_EXPIRED);
                    return;
                 }

        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}