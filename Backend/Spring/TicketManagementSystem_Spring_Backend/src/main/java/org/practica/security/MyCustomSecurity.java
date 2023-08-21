package org.practica.security;

import jakarta.servlet.http.HttpServletRequest;
import org.practica.exceptions.NoAuthorizationException;
import org.practica.exceptions.NoBearerInAuthorizationException;
import org.practica.exceptions.ResourceForbiddenException;
import org.practica.model.User;
import org.practica.repository.UsersRepository;
import org.practica.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyCustomSecurity {

    private static UsersRepository usersRepository;

    public MyCustomSecurity(UsersRepository usersRepository){
        MyCustomSecurity.usersRepository = usersRepository;
    }

    /**
     own implementation of validating a request to have authorization header
     it to contain a bearer with a JWT
     JWT giving the role of customer
     */
    public static void validateRequest(HttpServletRequest request, UserRoles userRole) throws
            NoAuthorizationException, NoBearerInAuthorizationException, ResourceForbiddenException{
        if(!requestContainsAuthorization(request)){
            throw new NoAuthorizationException("No authorization header in request!");
        }
        String jwt = MyCustomSecurity.extractJWT(request);
        String email = JwtUtil.extractEmail(jwt);
        boolean isCustomer = MyCustomSecurity.getUserRole(email);
        // if customer and role to check isn't customer
        if(isCustomer && userRole != UserRoles.CUSTOMER){
            throw new ResourceForbiddenException("Resource not allowed!");
        }
        // if admin and role to check isn't admin
        if(!isCustomer && userRole != UserRoles.ADMIN){
            throw new ResourceForbiddenException("Resource not allowed!");
        }
    }

    public static boolean requestContainsAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization") != null;
    }

    public static String extractJWT(HttpServletRequest request) throws NoBearerInAuthorizationException{
        String authorizationHeader = request.getHeader("Authorization");
        if(!authorizationHeader.startsWith("Bearer")){
            throw new NoBearerInAuthorizationException("Authorization header not starting with Bearer!");
        }
        return authorizationHeader.substring(7);
    }

    public static boolean getUserRole(String email) {
        return usersRepository.findRoleByEmail(email);
    }
}

