package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.webapp.dto.UserLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

@Component
public class TokenAuthenticationService {
  private static final String AUTH_HEADER = "Authorization";

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

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
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      UserLoginDTO userLogin = objectMapper.readValue(request.getInputStream(), UserLoginDTO.class);

      final UserDetails userDetails = userDetailsService.loadUserByUsername(userLogin.getEmail());

      if(userDetails != null) {
        if(passwordEncoder.matches(userLogin.getPassword(), userDetails.getPassword())) {
          return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        }
      }
    } catch (final Exception e) {
      return null;
    }
    return null;
  }
}
