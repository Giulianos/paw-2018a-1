package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.StatelessAuthenticationFilter;
import ar.edu.itba.paw.webapp.auth.StatelessLoginFilter;
import ar.edu.itba.paw.webapp.auth.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	private static final String API_PREFIX = "/api";

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, API_PREFIX + "/echo").hasAuthority("ROLE_USER")
				.antMatchers(HttpMethod.POST, API_PREFIX + "/echo").hasAuthority("ROLE_USER")

				// Publications
				.antMatchers(HttpMethod.POST, API_PREFIX + "/publications").hasAuthority("ROLE_USER")
				.antMatchers(HttpMethod.POST, API_PREFIX + "/publications/{\\d+}/images").hasAuthority("ROLE_USER")
				.antMatchers(HttpMethod.GET, API_PREFIX + "/users/{\\d+}/publications").hasAuthority("ROLE_USER")
				.antMatchers(HttpMethod.GET, API_PREFIX + "/publications/**").permitAll()

				// Orders
				.antMatchers(HttpMethod.POST, API_PREFIX + "/publications/{\\d+}/orders").hasAuthority("ROLE_USER")
				.antMatchers(HttpMethod.GET, API_PREFIX + "/users/{\\d+}/orders/**").hasAuthority("ROLE_USER")

				// User actions
				.antMatchers(HttpMethod.GET, API_PREFIX + "/users").hasAuthority("ROLE_USER")

				// Allow login and register for everyone
				.antMatchers(HttpMethod.POST, API_PREFIX + "/users").permitAll()
				.antMatchers(HttpMethod.POST, API_PREFIX + "/login").permitAll();

		http
				.addFilterBefore(new StatelessLoginFilter(API_PREFIX + "/login", tokenAuthenticationService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);

		    /*.exceptionHandling()
				.and()
				.exceptionHandling()*/
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		/* Here we indicate which pages we are allowed to see */
		web.ignoring()
			.antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityContext authenticationContext() {
		return SecurityContextHolder.getContext();
	}
	
}
