package ar.edu.itba.paw.webapp.controller;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.OrderService;
import ar.edu.itba.paw.interfaces.PublicationService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.auth.IAuthenticationFacade;
import ar.edu.itba.paw.webapp.form.UserForm;

@Controller
public class RegisterController {
    @Autowired
    private MessageSource messageSource;
	@Autowired
	private UserService us;
	@Autowired
	private PublicationService pu;
	@Autowired
	private OrderService ord;
	@Autowired
	private IAuthenticationFacade auth;
	@Autowired
	private Validator validator;
	@Autowired
	private EmailService emails;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	  binder.setValidator(validator);
	}
	
	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserForm form, ModelMap model) {

		return new ModelAndView("register");
	}

	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public ModelAndView register(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors, HttpServletRequest request, ModelMap model) {
		boolean isValid = validUser(form,model);

		if (errors.hasErrors() || !isValid) {
			return register(form, model);
		}

		
		final User u = us.create(form.getUsername(), form.getEmail(), form.getPassword());
		authWithoutPassword(u);
		emails.welcomeUser(u);
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
	
	private boolean validUser(UserForm form, ModelMap model) {
		boolean isValid = true;
		if(us.userExists(form.getUsername(), form.getEmail())) {
			isValid = false;
			model.addAttribute("invalidUser", true);
		}
		
		if (!form.passwordCheck()) {
			isValid = false;
			model.addAttribute("invalidPassword", true);
		}
		return isValid;
	}

}