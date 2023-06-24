package com.naggaro.javatest.service.impl;

import com.naggaro.javatest.utils.StringUtils;
import com.naggaro.javatest.constants.NaggaroConstants;
import com.naggaro.javatest.dto.GenericResponseDto;
import com.naggaro.javatest.dto.UserSessionDto;
import com.naggaro.javatest.exception.UnAutherizeException;
import com.naggaro.javatest.filters.AuthFilter;
import com.naggaro.javatest.service.LoginService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Service
public class LoginServiceImpl implements LoginService {



    @Override
    public GenericResponseDto<String> authenticateUser(ServletRequest request )  {
        HttpServletRequest req = (HttpServletRequest) request;

        String pass = req.getHeader("Authorization");
        String clientId = req.getHeader("ClientId");

        if(clientId== null  || pass==null)
        {
            throw new UnAutherizeException(NaggaroConstants.INVALID_TOKEN);
        }


        if(AuthFilter.loginSessions.get(clientId)!=null)
        {
            int diffInMinutes = (int)( (  new Date().getTime() - AuthFilter.loginSessions.get(clientId).getLoginTime().getTime()) / (1000 * 60  ) );
            if(diffInMinutes > NaggaroConstants.TOKEN_EXPIRY_TIME_IN_MINUTES)
                AuthFilter.loginSessions.remove(clientId);

            throw new UnAutherizeException(NaggaroConstants.USER_ALREADY_LOGGEDIN);

        }
        if((clientId.equalsIgnoreCase(NaggaroConstants.ADMIN_USER_NAME) && pass.equals(NaggaroConstants.ADMIN_PASS))
                ||
                (clientId.equalsIgnoreCase(NaggaroConstants.USER_NAME) && pass.equals(NaggaroConstants.USER_PASS)))

        {
            StringUtils token = new StringUtils();
            String generatedTokn= token.nextString();

            AuthFilter.loginSessions.put(clientId,new UserSessionDto(clientId,generatedTokn,new Date()));
            return GenericResponseDto.newSuccessInstance(generatedTokn);

        }

        throw new UnAutherizeException(NaggaroConstants.INVALID_TOKEN_OR_EXPIRED);


    }


    @Override
    public GenericResponseDto<String> logoutUser(ServletRequest request )  {
        HttpServletRequest req = (HttpServletRequest) request;

        String clientId = req.getHeader("ClientId");
        if(AuthFilter.loginSessions.get(clientId)==null)
        {
            return GenericResponseDto.newFailedInstance(NaggaroConstants.USER_NOT_FOUND);
        }
        AuthFilter.loginSessions.remove(clientId);

        return GenericResponseDto.newSuccessInstance(NaggaroConstants.LOGOUT_TEXT);

    }

}
