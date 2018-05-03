package ar.edu.itba.paw.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	private IAuthenticationFacade auth;
	 
	@RequestMapping("/user/{id}")
	public ModelAndView helloWorld(@PathVariable("id") int id) {
		final ModelAndView mav = new ModelAndView("user");
		mav.addObject("user", us.findById(id));
		return mav;
	}
	
	@RequestMapping("/")
	public ModelAndView index(@ModelAttribute("registerForm") final UserForm form, @ModelAttribute("publicationForm") final PublicationForm form2) {
		return new ModelAndView("index");
	}
	
	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors, ModelMap model) {
		boolean isValid = validUser(form,model);

		if (errors.hasErrors() || !isValid) {
			return index(form, null);
		}
		final User u = us.create(form.getUsername(), form.getEmail(), form.getPassword());
		return new ModelAndView("redirect:/user/"+ u.getId());
	}
	
	@RequestMapping(value = "/createPublication", method = { RequestMethod.POST })
	public ModelAndView createPublication(@Valid @ModelAttribute("publicationForm") final PublicationForm form, final BindingResult errors, ModelMap model) {
		if (errors.hasErrors()) {
			model.addAttribute("publicationErrors", true);
			return index(null, form);
		}
		model.addAttribute("publicationCreated", true);
		final Publication p = pu.create(auth.getAuthentication().getName(), form.getDescription(), Float.parseFloat(form.getPrice()), Integer.parseInt(form.getQuantity()));
		return index(null, null);
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
}