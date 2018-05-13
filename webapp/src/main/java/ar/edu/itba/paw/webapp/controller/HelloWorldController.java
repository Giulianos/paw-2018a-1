package ar.edu.itba.paw.webapp.controller;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.Orders;
import ar.edu.itba.paw.interfaces.Publications;
import ar.edu.itba.paw.interfaces.Users;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.auth.IAuthenticationFacade;
import ar.edu.itba.paw.webapp.form.PublicationForm;
import ar.edu.itba.paw.webapp.form.UserForm;

@Controller
public class HelloWorldController {
	
	@Autowired
	private Users us;
	@Autowired
	private Publications pu;
	@Autowired
	private Orders ord;
	@Autowired
	private IAuthenticationFacade auth;
	@Autowired
	private Validator validator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	  binder.setValidator(validator);
	}
	
	@RequestMapping("/")
	public ModelAndView index(@ModelAttribute("registerForm") final UserForm form, @ModelAttribute("publicationForm") final PublicationForm form2, ModelMap model) {
		includeUserTransactions(model);
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors, ModelMap model) {
		boolean isValid = validUser(form,model);

		if (errors.hasErrors() || !isValid) {
			return index(form, null, model);
		}

		includeUserTransactions(model);
		
		final User u = us.create(form.getUsername(), form.getEmail(), form.getPassword());
		authWithoutPassword(u);
		return new ModelAndView("redirect:/profile");
	}
	
	private void authWithoutPassword(User user){
		final Collection<? extends GrantedAuthority> authorities;
		if(user.getUsername().equals("administrator")) {
			authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}
		org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	    Authentication authentication = new UsernamePasswordAuthenticationToken(springUser, null, authorities);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	private boolean validUser(UserForm form, ModelMap model) {
		boolean isValid = true;
		
		if (!us.uniqueUser(form.getUsername())) {
			isValid = false;
			model.addAttribute("invalidUser", true);
		}
		if (!us.uniqueEmail(form.getEmail())) {
			isValid = false;
			model.addAttribute("invalidEmail", true);
		}
		if (!form.passwordCheck()) {
			isValid = false;
			model.addAttribute("invalidPassword", true);
		}
		return isValid;
	}

	private void includeUserTransactions(ModelMap model) {	
		if (auth.getAuthentication().isAuthenticated()) {
			String user = auth.getAuthentication().getName();
			model.addAttribute("publications", pu.findBySupervisor(user));
		}
	}

//   Source: https://www.mkyong.com/spring-security/spring-security-check-if-user-is-from-remember-me-cookie/
//	 private boolean isRememberMeAuthenticated() {
//
//			Authentication authentication = 
//				SecurityContextHolder.getContext().getAuthentication();
//			if (authentication == null) {
//				return false;
//			}
//
//		    return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
//	 }
}