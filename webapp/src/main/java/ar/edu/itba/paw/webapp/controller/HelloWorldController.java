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

import ar.edu.itba.paw.interfaces.Users;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.UserForm;

@Controller
public class HelloWorldController {
	
	@Autowired
	private Users us;
	 
	@RequestMapping("/user/{id}")
	public ModelAndView helloWorld(@PathVariable("id") int id) {
		final ModelAndView mav = new ModelAndView("user");
		mav.addObject("user", us.findById(id));
		return mav;
	}
	
	@RequestMapping("/")
	public ModelAndView index(@ModelAttribute("registerForm") final UserForm form) {
		return new ModelAndView("index");
	}
	
	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors, ModelMap model) {
		boolean isValid = validUser(form,model);

		if (errors.hasErrors() || !isValid) {
			return index(form);
		}
		final User u = us.create(form.getUsername(), form.getEmail(), form.getPassword());
		return new ModelAndView("redirect:/user/"+ u.getId());
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	private boolean validUser(UserForm form, ModelMap model) {
		boolean isValid = true;
		// attribute is not found in jsp if value is null.
		Object modelObject = new Object();
		
		if (!uniqueUser(form)) {
			isValid = false;
			model.addAttribute("invalidUser", modelObject);
		}
		if (!uniqueEmail(form)) {
			isValid = false;
			model.addAttribute("invalidEmail", modelObject);
		}
		if (!form.passwordCheck()) {
			isValid = false;
			model.addAttribute("invalidPassword", modelObject);
		}
		return isValid;
	}

	private boolean uniqueUser(UserForm form) {
		return us.findByUsername(form.getUsername()) == null;
	}

	private boolean uniqueEmail(UserForm form) {
		return us.findByEmail(form.getEmail()) == null;
	}
}