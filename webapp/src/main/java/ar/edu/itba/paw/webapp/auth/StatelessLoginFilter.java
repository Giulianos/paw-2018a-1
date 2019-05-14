package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {
  private final TokenAuthenticationService tokenAuthenticationService;
  private final UserDetailsService userDetailsService;

  public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
    super(new AntPathRequestMatcher(urlMapping));
    this.userDetailsService = userDetailsService;
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.setAuthenticationManager(authenticationManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
    final Authentication authentication = tokenAuthenticationService.getAuthenticationForLogin(httpServletRequest);

    if(authentication == null) {
      throw new UserAuthenticationException("Authentication failed");
    }

    return authentication;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authResult.getName());

    tokenAuthenticationService.addAuthentication(response, userDetails);
    SecurityContextHolder.getContext().setAuthentication(authResult);
  }

  private class UserAuthenticationException extends AuthenticationException {
    UserAuthenticationException(String msg) {
      super(msg);
    }
  }
}
