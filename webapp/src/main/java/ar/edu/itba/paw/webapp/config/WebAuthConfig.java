package ar.edu.itba.paw.webapp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.config", "ar.edu.itba.paw.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private PawUserDetailsService userDetailsService;
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService)
			.sessionManagement()
				.invalidSessionUrl("/")
			.and().authorizeRequests()
				.antMatchers("/login").anonymous()
				.antMatchers("/admin/**").hasRole("ADMIN")
			.and().formLogin()
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.defaultSuccessUrl("/", false)
				.loginPage("/login")
			.and().rememberMe()
				.rememberMeParameter("j_rememberme")
				.userDetailsService(userDetailsService)
				.key("mysupersecretketthatnobodyknowsabout")
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
			.and().exceptionHandling()
				.accessDeniedPage("/403")
			.and().csrf().disable();
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		/* Here we indicate which pages we are allowed to see */
		web.ignoring()
			.antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
	}
}
