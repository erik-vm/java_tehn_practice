package conf.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import conf.security.handlers.ApiAuthFailureHandler;
import conf.security.handlers.ApiAuthSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.io.IOException;
import java.util.stream.Collectors;

public class ApiAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public ApiAuthenticationFilter(AuthenticationManager authenticationManager, String url) {
        super(url);

        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(new ApiAuthSuccessHandler());
        setAuthenticationFailureHandler(new ApiAuthFailureHandler());

        setSecurityContextRepository(new HttpSessionSecurityContextRepository());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {


        // Read info from HttpServletRequest.

        String loginData = request.getReader().lines().collect(Collectors.joining(""));

        // Use ObjectMapper to convert Json to LoginCredentials object.

        LoginCredentials loginCredentials = null;
        try {
            loginCredentials = new ObjectMapper().readValue(loginData, LoginCredentials.class);
        } catch (JsonProcessingException e) {
            throw new BadCredentialsException("", e);
        }

        // Info from LoginCredentials is used below.

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        loginCredentials.getUsername(),
                        loginCredentials.getPassword());

        return getAuthenticationManager().authenticate(token);
    }
}
