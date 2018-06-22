package ar.edu.itba.paw.webapp.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;

@Component
public class PawUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService us;
	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Optional<User> user = us.findByUsername(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("No user by the name " + username);
		}
		// Con esto le doy rol de admin a todos los usuarios
		//final Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		
		//De esta forma solo le doy rol de admin al usuario con username admin (se deberia agregar a la entidad user el atributo rol para esto)
		final Collection<? extends GrantedAuthority> authorities;
		if(user.get().getUsername().equals("administrator")) {
			authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}
		
		return new org.springframework.security.core.userdetails.User(username, user.get().getPassword(), authorities);
	}
}
