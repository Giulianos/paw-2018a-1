package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.webapp.dto.SampleEchoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

@Component
public class TokenAuthenticationService {
  private static final String AUTH_HEADER = "Authorization";

  @Autowired
  private UserDetailsService userDetailsService;

  private final TokenHandler tokenHandler;

  public TokenAuthenticationService() {
    this.tokenHandler = new JWTHandler();
  }

  Authentication getAuthentication(final HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER);
    Authentication authentication = null;

    if(token != null) {
      final String username = tokenHandler.getUsername(token);

      if(username != null) {
        try {
          final UserDetails user = userDetailsService.loadUserByUsername(username);
          authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new HashSet<>(user.getAuthorities()));
        } catch (UsernameNotFoundException e) {
          return null;
        }
      }
    }
    return authentication;
  }

  void addAuthentication(final HttpServletResponse response, final UserDetails userDetails) {
    final String token = tokenHandler.createToken(userDetails.getUsername());

    response.setHeader(AUTH_HEADER, token);
  }

  Authentication getAuthenticationForLogin(final HttpServletRequest request) {
    try {
      /** Here we should extract the username from the request */
      final UserDetails userDetails = userDetailsService.loadUserByUsername("giuliano");

      if(userDetails != null) {
        /** here we should check that the provided password matches against the stored in the db */
        if(true) {
          return new UsernamePasswordAuthenticationToken("giuliano", "thePassword", userDetails.getAuthorities());
        }
      }
    } catch (final Exception e) {
      return null;
    }
    return null;
  }
}
