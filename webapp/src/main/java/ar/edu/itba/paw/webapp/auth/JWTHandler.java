package ar.edu.itba.paw.webapp.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;

public class JWTHandler implements TokenHandler {
  private static final String KEY = "this key should be changed!";
  private final int VALID_PERIOD = 34560; // seconds

  @Override
  public String createToken(String username) {
    final Date expirationDate = Date.from(Instant.now().plusSeconds(VALID_PERIOD));

    final String jwt = Jwts.builder()
        .setSubject(username)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, KEY)
        .compact();

    return jwt;
  }

  @Override
  public String getUsername(String token) {
    try {
      final String username = Jwts.parser()
          .setSigningKey(KEY)
          .parseClaimsJws(token)
          .getBody()
          .getSubject();

      return username;
    } catch (final Exception e) {
      return null;
    }
  }

}
